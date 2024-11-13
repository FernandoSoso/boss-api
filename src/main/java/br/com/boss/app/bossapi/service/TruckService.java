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
        Truck t = this.repository.findByPlate(newTruck.getLicensePlate());

        if (t == null){
            return persist(newTruck);
        }
        else{
            throw new BadRequestException("Placa já cadastrada!");
        }
    }

    public SubmitTruckDTO update(Truck updattedTruck, String uuid) throws Exception {
        updattedTruck.setUuid(UUID.fromString(uuid));
        Truck t = this.repository.findByUuid(UUID.fromString(uuid));

        if (t == null){
            throw new BadRequestException("Caminhão não encontrado!");
        }
        else{
            t = this.repository.findByPlate(updattedTruck.getLicensePlate());

            if (t != null){
                if (!t.getUuid().toString().equals(uuid)) {
                    throw new BadRequestException("Placa já cadastrada!");
                }
            }
        }

        return persist(updattedTruck);
    }

    private SubmitTruckDTO persist(Truck t){
        t.setStatus(true);
        this.repository.save(t);

        return new SubmitTruckDTO() {
            @Override
            public String getUuid() {
                return t.getUuid().toString();
            }

            @Override
            public String getLicensePlate() {
                return t.getLicensePlate();
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
            public Double getCapacity() {
                return t.getCapacity();
            }

            @Override
            public Double getDriverPercentage() {
                return t.getDriverPercentage();
            }
        };
    }

    public void delete(String uuid) throws Exception {
        Truck t = this.repository.findByUuid(UUID.fromString(uuid));

        if (t == null){
            throw new BadRequestException("Caminhão não encontrado!");
        }

        t.setStatus(false);

        this.repository.save(t);
    }

    public List<TruckDTO> getAll() {
        return this.repository.getAllTrucks();
    }

    public UniqueTruckDTO getUnique(String uuid) throws Exception {
        Truck t = this.repository.findByUuid(UUID.fromString(uuid));

        if (t == null){
            throw new BadRequestException("Caminhão não encontrado!");
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
