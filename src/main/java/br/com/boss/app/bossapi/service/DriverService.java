package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.dto.driver.DriverDTO;
import br.com.boss.app.bossapi.dto.driver.SubmitDriverDTO;
import br.com.boss.app.bossapi.dto.driver.UniqueDriverDTO;
import br.com.boss.app.bossapi.dto.truck.TruckSummaryDTO;
import br.com.boss.app.bossapi.enums.SituationStatus;
import br.com.boss.app.bossapi.model.Driver;
import br.com.boss.app.bossapi.repository.DriverRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DriverService {

    private final DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public SubmitDriverDTO insert(Driver newDriver) throws Exception {
        Driver t = this.repository.findByLicenseNumber(newDriver.getLicenseNumber());

        if (t == null){
            return persist(newDriver);
        }
        else{
            throw new BadRequestException("CNH já cadastrada!");
        }
    }

    public SubmitDriverDTO update(Driver driverToUpdate, String driverUuid) throws Exception {
        Driver driver = this.repository.findByUuid(UUID.fromString(driverUuid));

        if (driver == null){
            throw new BadRequestException("Motorista não encontrado!");
        }
        else {
            driver = this.repository.findByLicenseNumber(driverToUpdate.getLicenseNumber());

            if (driver == null){
                return persist(driverToUpdate);
            }
            else{
                throw new BadRequestException("CNH já cadastrada!");
            }
        }
    }

    private SubmitDriverDTO persist(Driver d) {
        d.setStatus(true);
        this.repository.save(d);

        return new SubmitDriverDTO() {

            @Override
            public String getUuid() {
                return d.getUuid().toString();
            }

            @Override
            public String getName() {
                return d.getName();
            }

            @Override
            public String getLicenseNumber() {
                return d.getLicenseNumber();
            }

            @Override
            public Date getLicenseExpirationDate() {
                return d.getLicenseExpirationDate();
            }

            @Override
            public String getPrimaryPhone() {
                return d.getPrimaryPhone();
            }

            @Override
            public String getSecondaryPhone() {
                return d.getSecondaryPhone();
            }

            @Override
            public String getEmail() {
                return d.getEmail();
            }
        };
    }

    public void delete(String driverUuid) throws Exception{
        Driver driver = this.repository.findByUuid(UUID.fromString(driverUuid));

        if (driver == null){
            throw new BadRequestException("Motorista não encontrado!");
        }
        else{
            driver.setStatus(false);
            this.repository.save(driver);
        }
    }

    public UniqueDriverDTO getUnique(String driverUuid) throws Exception {
        Driver d = this.repository.findByUuid(UUID.fromString(driverUuid));

        if (d == null){
            throw new BadRequestException("Motorista não encontrado!");
        }

        TruckSummaryDTO t = this.repository.getTruck(d.getId());

        return new UniqueDriverDTO() {
            @Override
            public String getUuid() {
                return d.getUuid().toString();
            }

            @Override
            public String getName() {
                return d.getName();
            }

            @Override
            public String getLicenseNumber() {
                return d.getLicenseNumber();
            }

            @Override
            public String getLicenseExpirationDate() {
                return d.getLicenseExpirationDate().toString();
            }

            @Override
            public String getPrimaryPhone() {
                return d.getPrimaryPhone();
            }

            @Override
            public String getSecondaryPhone() {
                return d.getSecondaryPhone();
            }

            @Override
            public String getEmail() {
                return d.getEmail();
            }

            @Override
            public TruckSummaryDTO getTruck() {
                return t;
            }

            @Override
            public SituationStatus getStatus() {
                return d.getDriverStatus();
            }
        };
    }

    public List<DriverDTO> getAll() {
        return this.repository.getAllDrivers();
    }
}
