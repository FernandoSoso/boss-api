package br.com.boss.app.bossapi.dto.truck.response;

import br.com.boss.app.bossapi.enums.TruckStatus;

public record TruckDTO(String id,
                       String licensePlate,
                       String brand,
                       String model,
                       Integer year,
                       Double capacity,
                       TruckStatus status) { }