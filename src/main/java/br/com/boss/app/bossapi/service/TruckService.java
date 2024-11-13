package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.dto.driver.DriverSummaryDTO;
import br.com.boss.app.bossapi.dto.truck.SubmitTruckDTO;
import br.com.boss.app.bossapi.dto.truck.TruckDTO;
import br.com.boss.app.bossapi.dto.truck.UniqueTruckDTO;
import br.com.boss.app.bossapi.enums.SituationStatus;
import br.com.boss.app.bossapi.model.Truck;
import br.com.boss.app.bossapi.repository.TruckRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TruckService {
    private final TruckRepository repository;

    public TruckService(TruckRepository repository){
        this.repository = repository;
    }

    public SubmitTruckDTO insert(Truck newTruck) throws Exception {
        return persist(newTruck);
    }

    public SubmitTruckDTO update(Truck updattedTruck, String uuid) throws Exception {
        Truck t = this.repository.findByUuid(UUID.fromString(uuid));

        if (t == null){
            return null;
        }

        t.setBrand(updattedTruck.getBrand());
        t.setModel(updattedTruck.getModel());
        t.setYear(updattedTruck.getYear());
        t.setCapacity(updattedTruck.getCapacity());
        t.setDriverPercentage(updattedTruck.getDriverPercentage());
        t.setLicensePlate(updattedTruck.getLicensePlate());
        t.setTruckStatus(updattedTruck.getTruckStatus());

        return persist(t);
    }

    private SubmitTruckDTO persist(Truck truckToPersist) throws Exception {
        Truck t = this.repository.findByPlate(truckToPersist.getLicensePlate());

        if (t != null){
            if (!t.getUuid().equals(truckToPersist.getUuid())) {
                throw new BadRequestException("Placa já cadastrada!");
            }
        }

        truckToPersist.setStatus(true);
        this.repository.save(truckToPersist);

        return new SubmitTruckDTO() {
            @Override
            public String getUuid() {
                return truckToPersist.getUuid().toString();
            }

            @Override
            public String getLicensePlate() {
                return truckToPersist.getLicensePlate();
            }

            @Override
            public String getBrand() {
                return truckToPersist.getBrand();
            }

            @Override
            public String getModel() {
                return truckToPersist.getModel();
            }

            @Override
            public Integer getYear() {
                return truckToPersist.getYear();
            }

            @Override
            public Double getCapacity() {
                return truckToPersist.getCapacity();
            }

            @Override
            public Double getDriverPercentage() {
                return truckToPersist.getDriverPercentage();
            }
        };
    }

    public boolean delete(String uuid) {
        Truck t = this.repository.findByUuid(UUID.fromString(uuid));

        if (t == null){
            return false;
        }

        t.setStatus(false);

        this.repository.save(t);
        return true;
    }

    public List<TruckDTO> getAll() {
        return this.repository.getAllTrucks();
    }

    public UniqueTruckDTO getUnique(String uuid) throws Exception {
        Truck t = this.repository.findByUuid(UUID.fromString(uuid));

        if (t == null){
            return null;
        }

        DriverSummaryDTO d = this.repository.getDriver(t.getId());

        return new UniqueTruckDTO() {
            @Override
            public String getUuid() {
                return t.getUuid().toString();
            }

            @Override
            public Double getCapacity() {
                return t.getCapacity();
            }

            @Override
            public String getBrand() {
                return t.getBrand();
            }

            @Override
            public String getModel() {
                return t.getModel();
            }

            @Override
            public Integer getYear() {
                return t.getYear();
            }

            @Override
            public String getLicensePlate() {
                return t.getLicensePlate();
            }

            @Override
            public Double getDriverPercentage() {
                return t.getDriverPercentage();
            }

            @Override
            public DriverSummaryDTO getDriver() {
                return d;
            }

            @Override
            public SituationStatus getStatus() {
                return t.getTruckStatus();
            }
        };
    }
}
