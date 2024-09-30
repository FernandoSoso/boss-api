package br.com.boss.app.bossapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@Getter
@Setter
public class Driver_Truck {
    private final Long idMotorista;
    private final Long idCaminhao;
    private Date startDate;
    private Date endDate;
}
