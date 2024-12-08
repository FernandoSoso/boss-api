package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.dto.delivery.DeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.InputDeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.SubmitDeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.UniqueDeliveryDTO;
import br.com.boss.app.bossapi.dto.driver.DriverSummaryDTO;
import br.com.boss.app.bossapi.dto.driver_truck.EntryDTO;
import br.com.boss.app.bossapi.dto.truck.TruckSummaryDTO;
import br.com.boss.app.bossapi.enums.DeliveryStatus;
import br.com.boss.app.bossapi.model.Delivery;
import br.com.boss.app.bossapi.model.Driver;
import br.com.boss.app.bossapi.model.Driver_Truck;
import br.com.boss.app.bossapi.model.Truck;
import br.com.boss.app.bossapi.repository.DeliveryRepository;
import br.com.boss.app.bossapi.repository.DriverTruckRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Tag(name = "Serviço de entregas", description = "Serviço de entregas")
public class DeliveryService {
    private final DeliveryRepository repository;
    private final DriverTruckRepository driverTruckRepository;

    public DeliveryService(DeliveryRepository repository, DriverTruckRepository driverTruckRepository) {
        this.repository = repository;
        this.driverTruckRepository = driverTruckRepository;
    }

    public SubmitDeliveryDTO insert(InputDeliveryDTO newDelivery) throws Exception {
        Delivery delivery = new Delivery();

        return persist(delivery, newDelivery);
    }

    public SubmitDeliveryDTO update(InputDeliveryDTO deliveryToUpdate, String deliveryUuid) throws Exception {
        Delivery delivery = this.repository.getByUuid(UUID.fromString(deliveryUuid));

        if (delivery == null) {
            return null;
        }

        return persist(delivery, deliveryToUpdate);
    }

    private SubmitDeliveryDTO persist(Delivery deliveryToPersist, InputDeliveryDTO deliveryData) throws BadRequestException {
        Driver_Truck currentEntry = this.driverTruckRepository.getByBothUuids(
                UUID.fromString(deliveryData.responsible().driverUuid()),
                UUID.fromString(deliveryData.responsible().truckUuid())
        );

        if (currentEntry == null) {
            throw new BadRequestException("A entrada do caminhão e seu motorista não foi encontrada!");
        }

        // Definir dados da entrega
        // Dados Object
        deliveryToPersist.setDriver_truck(currentEntry);
        deliveryToPersist.setDestination(deliveryData.destination());
        deliveryToPersist.setOrigin(deliveryData.origin());
        deliveryToPersist.setObservation(deliveryData.observation());

        if (deliveryToPersist.getDeliveryStatus() == null) {
            deliveryToPersist.setDeliveryStatus(DeliveryStatus.EM_PROGRESSO);
        }

        // Dados Double (Arrendondado para 2 casas decimais)
        deliveryToPersist.setDriverShare(roundUp(deliveryData.driverShare(), 2));
        deliveryToPersist.setValuePerTon(roundUp(deliveryData.valuePerTon(), 2));
        deliveryToPersist.setWeight(roundUp(deliveryData.weight(), 2));

        //setar o valor bruto (Arrendondado para 2 casas decimais)
        deliveryToPersist.setGrossValue(roundUp(deliveryToPersist.getWeight() * deliveryToPersist.getValuePerTon(), 2));

        //setar o valor liquido (Arrendondado para 2 casas decimais)
        deliveryToPersist.setNetValue(roundUp(deliveryToPersist.getGrossValue() - deliveryToPersist.getDriverShare(), 2));

        // Definir status do dado
        deliveryToPersist.setStatus(true);

        this.repository.save(deliveryToPersist);

        return new SubmitDeliveryDTO(){

            @Override
            public String getUuid() {
                return deliveryToPersist.getUuid().toString();
            }

            @Override
            public String getOrigin() {
                return deliveryToPersist.getOrigin();
            }

            @Override
            public String getDestination() {
                return deliveryToPersist.getDestination();
            }

            @Override
            public Double getValuePerTon() {
                return deliveryToPersist.getValuePerTon();
            }

            @Override
            public Double getGrossValue() {
                return deliveryToPersist.getGrossValue();
            }

            @Override
            public Double getNetValue() {
                return deliveryToPersist.getNetValue();
            }

            @Override
            public Double getWeight() {
                return deliveryToPersist.getWeight();
            }

            @Override
            public EntryDTO getResponsible() {
                return new EntryDTO(
                        deliveryToPersist.getDriver_truck().getDriver().getUuid().toString(),
                        deliveryToPersist.getDriver_truck().getTruck().getUuid().toString()
                );
            }

            @Override
            public String getObservation() {
                return deliveryToPersist.getObservation();
            }

            @Override
            public Double getDriverShare() {
                return deliveryToPersist.getDriverShare();
            }
        };
    }

    private Double roundUp(Double value, Integer decimalPlaces) {
        return Math.round(value * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
    }

    public boolean delete(String deliveryrUuid) throws Exception {
        Delivery delivery = this.repository.getByUuid(UUID.fromString(deliveryrUuid));

        if (delivery == null) {
            return false;
        }

        delivery.setStatus(false);
        this.repository.save(delivery);
        return true;
    }

    public UniqueDeliveryDTO getUnique(String deliveryUuid) throws Exception {
        Delivery d = this.repository.getByUuid(UUID.fromString(deliveryUuid));

        if (d == null) {
            return null;
        }

        Driver driver = d.getDriver_truck().getDriver();
        Truck truck = d.getDriver_truck().getTruck();

        return new UniqueDeliveryDTO(
                d.getUuid().toString(),
                new DriverSummaryDTO() {
                    @Override
                    public String getUuid() {
                        return driver.getUuid().toString();
                    }

                    @Override
                    public String getName() {
                        return driver.getName();
                    }

                    @Override
                    public String getLicenseNumber() {
                        return driver.getLicenseNumber();
                    }

                    @Override
                    public Date getLicenseExpirationDate() {
                        return driver.getLicenseExpirationDate();
                    }

                    @Override
                    public Date getStartDate() {
                        return d.getDriver_truck().getStartDate();
                    }
                },
                new TruckSummaryDTO() {
                    @Override
                    public String getUuid() {
                        return truck.getUuid().toString();
                    }

                    @Override
                    public String getLicensePlate() {
                        return truck.getLicensePlate();
                    }

                    @Override
                    public Double getDriverPercentage() {
                        return truck.getDriverPercentage();
                    }

                    @Override
                    public Double getCapacity() {
                        return truck.getCapacity();
                    }

                    @Override
                    public Date getStartDate() {
                        return d.getDriver_truck().getStartDate();
                    }
                },
                d.getOrigin(),
                d.getDestination(),
                d.getNetValue(),
                d.getGrossValue(),
                d.getDriverShare(),
                d.getDeliveryStatus(),
                d.getValuePerTon(),
                d.getWeight(),
                d.getObservation()
        );
    }

    public List<DeliveryDTO> getAll() {
        return this.repository.getAllDeliveries();
    }
}
