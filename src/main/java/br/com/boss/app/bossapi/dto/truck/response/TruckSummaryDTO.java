package br.com.boss.app.bossapi.dto.truck.response;

import java.util.Date;

public record TruckSummaryDTO (String id,
                               String licensePlate,
                               Double driverPercentage,
                               Integer capacity,
                               Date startDate) { }