package br.com.boss.app.bossapi.dto.delivery;

import br.com.boss.app.bossapi.dto.driver.DriverSummaryDTO;
import br.com.boss.app.bossapi.dto.truck.TruckSummaryDTO;
import br.com.boss.app.bossapi.enums.DeliveryStatus;

public record UniqueDeliveryDTO (
    String uuid,
    DriverSummaryDTO driver,
    TruckSummaryDTO truck,
    String origin,
    String destination,
    Double netValue,
    Double grossValue,
    Double driverShare,
    DeliveryStatus status,
    Double valuePerTon,
    Double weight,
    String observation
) {}
