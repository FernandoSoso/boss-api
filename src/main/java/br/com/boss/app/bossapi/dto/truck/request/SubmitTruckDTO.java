package br.com.boss.app.bossapi.dto.truck.request;

public record SubmitTruckDTO(String licensePlate,
                             String brand,
                             String model,
                             Integer year,
                             Integer capacity,
                             Double driverPercentage,
                             String driverId) { }