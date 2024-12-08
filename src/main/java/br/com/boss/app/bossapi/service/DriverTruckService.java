package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.dto.driver_truck.EntryDTO;
import br.com.boss.app.bossapi.dto.driver_truck.Driver_TruckDTO;
import br.com.boss.app.bossapi.model.Driver;
import br.com.boss.app.bossapi.model.Driver_Truck;
import br.com.boss.app.bossapi.model.Truck;
import br.com.boss.app.bossapi.repository.DriverRepository;
import br.com.boss.app.bossapi.repository.DriverTruckRepository;
import br.com.boss.app.bossapi.repository.TruckRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
@Tag(name = "Serviço de motorista-caminhão", description = "Serviço de motorista-caminhão")
public class DriverTruckService {
    private final DriverTruckRepository repository;
    private final DriverRepository driverRepository;
    private final TruckRepository truckRepository;

    public DriverTruckService(
            DriverTruckRepository repository,
            DriverRepository driverRepository,
            TruckRepository truckRepository
    ){
        this.repository = repository;
        this.driverRepository = driverRepository;
        this.truckRepository = truckRepository;
    }

    public boolean changeHistory(EntryDTO historyDTO) throws Exception {
        Driver_Truck driverHistory = this.repository.findLastOfDriverUuid(UUID.fromString(historyDTO.driverUuid()));
        Driver_Truck truckHistory = this.repository.findLastOfTruckUuid(UUID.fromString(historyDTO.truckUuid()));

        Driver driver;
        Truck truck;

        if (driverHistory != null){
            driver = driverHistory.getDriver();
            driverHistory.setEndDate(new Date(System.currentTimeMillis()));
            this.repository.save(driverHistory);
        }
        else{
            driver = this.driverRepository.findByUuid(UUID.fromString(historyDTO.driverUuid()));

            if (driver == null){
                throw new BadRequestException("Motorista não encontrado!");
            }
        }

        if (truckHistory != null){
            truck = truckHistory.getTruck();
            truckHistory.setEndDate(new Date(System.currentTimeMillis()));
            this.repository.save(truckHistory);
        }
        else{
            truck = this.truckRepository.findByUuid(UUID.fromString(historyDTO.truckUuid()));

            if (truck == null){
                throw new BadRequestException("Caminhão não encontrado!");
            }
        }

        if (driverHistory != null && truckHistory != null){
            if (driverHistory.getId().equals(truckHistory.getId())){
                return false;
            }
        }

        Driver_Truck newHistory = new Driver_Truck();

        newHistory.setDriver(driver);
        newHistory.setTruck(truck);
        newHistory.setStartDate(new Date(System.currentTimeMillis()));

        this.repository.save(newHistory);
        return true;
    }

    public List<Driver_TruckDTO> getTruckHistory(String truckUuid) {
        Truck truck = this.truckRepository.findByUuid(UUID.fromString(truckUuid));

        if (truck == null){
            return null;
        }

        return this.repository.findByTruckUuid(UUID.fromString(truckUuid));
    }

    public List<Driver_TruckDTO> getDriverHistory(String uuid) {
        Driver driver = this.driverRepository.findByUuid(UUID.fromString(uuid));

        if (driver == null){
            return null;
        }

        return this.repository.findByDriverUuid(UUID.fromString(uuid));
    }

    public boolean endHistory(String uuid) throws Exception {
        Driver_Truck driverTruck = this.repository.findByUuid(UUID.fromString(uuid));

        if (driverTruck == null){
            return false;
        }

        if (driverTruck.getEndDate() != null){
            throw new BadRequestException("Entrada já finalizada!");
        }

        driverTruck.setEndDate(new Date(System.currentTimeMillis()));
        this.repository.save(driverTruck);
        return true;
    }

}
