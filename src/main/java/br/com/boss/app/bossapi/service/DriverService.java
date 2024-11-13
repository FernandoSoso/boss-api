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
        return persist(newDriver);
    }

    public SubmitDriverDTO update(Driver driverToUpdate, String driverUuid) throws Exception {
        Driver d = this.repository.findByUuid(UUID.fromString(driverUuid));

        if (d == null){
            return null;
        }

        d.setDriverStatus(driverToUpdate.getDriverStatus());
        d.setEmail(driverToUpdate.getEmail());
        d.setLicenseExpirationDate(driverToUpdate.getLicenseExpirationDate());
        d.setLicenseNumber(driverToUpdate.getLicenseNumber());
        d.setName(driverToUpdate.getName());
        d.setPrimaryPhone(driverToUpdate.getPrimaryPhone());
        d.setSecondaryPhone(driverToUpdate.getSecondaryPhone());

        return persist(d);
    }

    private SubmitDriverDTO persist(Driver driverToPersist) throws Exception {
        Driver d = this.repository.findByLicenseNumber(driverToPersist.getLicenseNumber());

        if (d != null){
            if (!d.getUuid().equals(driverToPersist.getUuid())) {
                throw new BadRequestException("CNH já cadastrada!");
            }
        }

        driverToPersist.setStatus(true);
        this.repository.save(driverToPersist);

        return new SubmitDriverDTO() {

            @Override
            public String getUuid() {
                return driverToPersist.getUuid().toString();
            }

            @Override
            public String getName() {
                return driverToPersist.getName();
            }

            @Override
            public String getLicenseNumber() {
                return driverToPersist.getLicenseNumber();
            }

            @Override
            public Date getLicenseExpirationDate() {
                return driverToPersist.getLicenseExpirationDate();
            }

            @Override
            public String getPrimaryPhone() {
                return driverToPersist.getPrimaryPhone();
            }

            @Override
            public String getSecondaryPhone() {
                return driverToPersist.getSecondaryPhone();
            }

            @Override
            public String getEmail() {
                return driverToPersist.getEmail();
            }
        };
    }

    public boolean delete(String driverUuid) throws Exception{
        Driver driver = this.repository.findByUuid(UUID.fromString(driverUuid));

        if (driver == null){
            return false;
        }

        driver.setStatus(false);
        this.repository.save(driver);
        return true;
    }

    public UniqueDriverDTO getUnique(String driverUuid) throws Exception {
        Driver d = this.repository.findByUuid(UUID.fromString(driverUuid));

        if (d == null){
            return null;
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
