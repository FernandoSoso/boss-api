package br.com.boss.app.bossapi.repository;

import br.com.boss.app.bossapi.dto.delivery.DeliveryDTO;
import br.com.boss.app.bossapi.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    @Query(value = """
    SELECT * FROM Delivery d
        WHERE d.uuid = :uuid and d.status = true
    """, nativeQuery = true)
    Delivery getByUuid(@Param("uuid") UUID uuid);

    @Query(value = """
    SELECT
        d.uuid,
        d.origin,
        d.observation,
        d.delivery_status as deliveryStatus,
        d.gross_value as grossValue,
        d.weight,
        d.destination
        FROM Delivery d
        WHERE d.status = true
    """, nativeQuery = true)
    List<DeliveryDTO> getAllDeliveries();
}
