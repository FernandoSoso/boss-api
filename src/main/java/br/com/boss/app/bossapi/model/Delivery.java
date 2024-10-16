package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@ToString
@Getter
@Setter
@Table(name = "delivery")
@Entity
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @NonNull
    private String origin;

    @NonNull
    private String destination;

    @NonNull
    private Double valuePerTon;

    @NonNull
    private Double weight;

    private String observation;

    @NonNull
    @Embedded
    private DeliveryStatus status;

    @OneToOne(cascade = CascadeType.PERSIST,
            optional = false,
            orphanRemoval = true,
            targetEntity = Driver_Truck.class)
    private Driver_Truck driver_truck;

    private Double netValue;
    private Double grossValue;
    private Double driverShare;
}