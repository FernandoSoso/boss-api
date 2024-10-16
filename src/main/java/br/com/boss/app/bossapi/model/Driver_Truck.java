package br.com.boss.app.bossapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "driver_truck")
public class Driver_Truck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST,
            targetEntity = Driver.class)
    private Long driver;

    @OneToOne(cascade = CascadeType.PERSIST,
            targetEntity = Truck.class)
    private Long truck;

    @NonNull
    private Date startDate;

    private Date endDate;
}
