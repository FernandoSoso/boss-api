package br.com.boss.app.bossapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.NonNull;

import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "truck")
public class Truck {

    public Truck() {}

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @NonNull
    private String licensePlate;
    private String brand;
    private String model;
    private Integer year;
    private Integer capacity;
    private Double driverPercentage;
}