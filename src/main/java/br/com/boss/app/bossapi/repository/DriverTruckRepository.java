package br.com.boss.app.bossapi.repository;

import br.com.boss.app.bossapi.dto.driver_truck.Driver_TruckDTO;
import br.com.boss.app.bossapi.model.Driver_Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DriverTruckRepository extends JpaRepository<Driver_Truck, Long> {
    @Query(value = """
        SELECT
            dt.uuid,
            dt.start_date,
            dt.end_date,
            dt.id,
            dt.truck_id,
            dt.driver_id
            FROM driver_truck as dt
            JOIN public.driver d on d.id = dt.driver_id
            WHERE d.uuid = :driverUuid AND dt.end_date is null;
        """, nativeQuery = true)
    Driver_Truck findLastOfDriverUuid(@Param("driverUuid") UUID driverUuid);

    @Query(value = """
        SELECT
            dt.uuid,
            dt.start_date,
            dt.end_date,
            dt.id,
            dt.truck_id,
            dt.driver_id
            FROM driver_truck as dt
            JOIN public.truck t on t.id = dt.driver_id
            WHERE t.uuid = :truckUuid AND dt.end_date is null;
        """,
            nativeQuery = true)
    Driver_Truck findLastOfTruckUuid(@Param("truckUuid") UUID truckUuid);

    @Query(value = """
        SELECT
            dt.uuid,
            dt.start_date as startDate,
            dt.end_date as endDate,
            t.license_plate as entryIdentification
            FROM driver_truck as dt
            JOIN public.truck t on t.id = dt.truck_id
            WHERE t.uuid = :truckUuid;
        """, nativeQuery = true)
    List<Driver_TruckDTO> findByTruckUuid(@Param("truckUuid") UUID truckUuid);

    @Query(value = """
        SELECT
            dt.uuid,
            dt.start_date as startDate,
            dt.end_date as endDate,
            d.name as entryIdentification
            FROM driver_truck as dt
            JOIN public.driver d on d.id = dt.driver_id
            WHERE d.uuid = :driverUuid;
        """, nativeQuery = true)
    List<Driver_TruckDTO> findByDriverUuid(@Param("driverUuid") UUID driverUuid);

    @Query(value = """
        SELECT
            dt.uuid,
            dt.start_date,
            dt.end_date,
            dt.id,
            dt.truck_id,
            dt.driver_id
            FROM driver_truck as dt
            WHERE dt.uuid = :uuid;
        """, nativeQuery = true)
    Driver_Truck findByUuid(@Param("uuid") UUID uuid);

    @Query(value = """
        SELECT
            dt.uuid,
            dt.start_date,
            dt.end_date,
            dt.id,
            dt.truck_id,
            dt.driver_id
            FROM driver_truck as dt
            join public.driver d on d.id = dt.driver_id
            join public.truck t on t.id = dt.truck_id
            WHERE d.uuid = :driverUuid AND t.uuid = :truckUuid AND dt.end_date is null;
        """, nativeQuery = true)
    Driver_Truck getByBothUuids(@Param("driverUuid") UUID driverUuid, @Param("truckUuid") UUID truckUuid);
}
