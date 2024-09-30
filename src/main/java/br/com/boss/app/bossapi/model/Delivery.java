package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Delivery {

    private Long id;
    private String uuid;
    private String origin;
    private String destination;
    private Double valuePerTon;
    private Double weight;
    private String observation;
    private DeliveryStatus status;
    private Driver driver;
    private Truck vehicle;

    private Double netValue;
    private Double grossValue;
    private Double driverShare;
}