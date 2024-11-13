package br.com.boss.app.bossapi.repository;

import br.com.boss.app.bossapi.dto.driver.DriverDTO;
import br.com.boss.app.bossapi.dto.truck.TruckSummaryDTO;
import br.com.boss.app.bossapi.model.Driver;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;


public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = """
        SELECT
            id,
            uuid,
            name,
            primary_phone,
            secondary_phone,
            email,
            license_number,
            license_expiration_date,
            driver_status,
            status
        FROM driver
        WHERE uuid = :uuid and status = true
        """, nativeQuery = true)
    Driver findByUuid(@Param("uuid") UUID uuid);

    @Query(value = """
        SELECT
            id,
            uuid,
            name,
            primary_phone,
            secondary_phone,
            email,
            license_number,
            license_expiration_date,
            driver_status,
            status
        FROM driver
        WHERE license_number = :licenseNumber and status = true
        """, nativeQuery = true)
    Driver findByLicenseNumber(@NotNull @NotBlank String licenseNumber);

    @Query(value = """
        SELECT
            id,
            uuid,
            name,
            primary_phone,
            secondary_phone,
            email,
            license_number,
            license_expiration_date,
            driver_status,
            status
        FROM driver
        WHERE status = true
        """, nativeQuery = true)
    List<DriverDTO> getAllDrivers();

    @Query(value = """
        SELECT
            t.uuid,
            license_plate as licensePlate,
            driver_percentage as driverPercentage,
            capacity,
            start_date as startDate
        FROM truck as t
        JOIN driver_truck as dt on t.id = dt.truck_id
        WHERE dt.driver_id = :driverId and dt.end_date is null
        """, nativeQuery = true)
    TruckSummaryDTO getTruck(@Param("driverId") Long driverId);
}
