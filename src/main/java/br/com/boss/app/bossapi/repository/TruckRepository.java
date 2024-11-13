package br.com.boss.app.bossapi.repository;

import br.com.boss.app.bossapi.dto.driver.DriverSummaryDTO;
import br.com.boss.app.bossapi.dto.truck.TruckDTO;
import br.com.boss.app.bossapi.model.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    @Query(value = """
        SELECT
            t.id,
            t.uuid,
            t.license_plate,
            t.model,
            t.brand,
            t.capacity,
            t.year,
            t.driver_percentage,
            t.truck_status,
            t.status
        FROM truck AS t
        WHERE upper(t.license_plate) = upper(:plate) AND t.status = true
        \s""", nativeQuery = true)
    Truck findByPlate(@Param("plate") String plate);

    @Query(value = """
        SELECT
            t.uuid,
            t.license_plate as licensePlate,
            t.brand,
            t.model,
            t.year,
            t.capacity,
            t.truck_status as status
            FROM truck AS t
            WHERE t.status = true
        """, nativeQuery = true)
    List<TruckDTO> getAllTrucks();

    @Query(value = """
        SELECT
            t.id,
            t.uuid,
            t.license_plate,
            t.model,
            t.brand,
            t.capacity,
            t.year,
            t.driver_percentage,
            t.truck_status,
            t.status
        FROM truck AS t
        WHERE t.uuid = :uuid and t.status = true
        """, nativeQuery = true)
    Truck findByUuid(@Param("uuid") UUID uuid);

    @Query(value = """
        SELECT
            dt.uuid,
            dr.name,
            dr.license_number as licenseNumber,
            dr.license_expiration_date as licenseExpirationDate,
            dt.start_date as startDate
            FROM driver_truck as dt
            Join driver as dr on dt.driver_id = dr.id
            where dt.truck_id = :truckId AND dt.end_date is null;
    """, nativeQuery = true)
    DriverSummaryDTO getDriver(@Param("truckId") Long truckId);
}