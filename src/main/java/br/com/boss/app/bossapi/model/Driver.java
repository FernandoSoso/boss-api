package br.com.boss.app.bossapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class Driver {
    private Long id;
    private String uuid;
    private String name;
    private String primaryPhone;
    private String secondaryPhone;
    private String email;
    private String licenseNumber;
    private Date licenseExpirationDate;
}