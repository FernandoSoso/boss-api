package br.com.boss.app.bossapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "driver", uniqueConstraints = {
    @UniqueConstraint(columnNames = "licenseNumber"),
    @UniqueConstraint(columnNames = "email")
})
public class Driver {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    private String uuid;

    @NonNull
    private String name;

    @NonNull
    private String primaryPhone;

    private String secondaryPhone;

    @NonNull
    private String email;

    @NonNull
    private String licenseNumber;

    @NonNull
    private Date licenseExpirationDate;
}