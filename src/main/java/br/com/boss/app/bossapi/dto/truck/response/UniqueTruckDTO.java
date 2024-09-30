package br.com.boss.app.bossapi.dto.truck.response;

import br.com.boss.app.bossapi.dto.driver.response.DriverSummaryDTO;

public record UniqueTruckDTO(String id,
                             String licensePlate,
                             String brand,
                             String model,
                             Integer year,
                             Double capacity,
                             Double driverPercentage,
                             DriverSummaryDTO driver) {

}