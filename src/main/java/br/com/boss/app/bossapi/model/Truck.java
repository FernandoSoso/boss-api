package br.com.boss.app.bossapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class Truck {

    public Truck() {}

    private Long id;
    private String uuid;
    private String licensePlate;
    private String brand;
    private String model;
    private Integer year;
    private Integer capacity;
    private Double driverPercentage;
    private Driver driver;
}