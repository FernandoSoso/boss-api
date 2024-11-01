package br.com.boss.app.bossapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @NotNull
    @Size(min = 7, max = 7, message = "Placa do caminhão inválida! (Deve ter 7 caracteres)")
    private String licensePlate;
    
    @NotBlank
    @Size(min = 3, max = 50, message = "Marca do caminhão inválida! (Deve ter entre 3 e 50 caracteres)")
    private String brand;
    
    @NotBlank
    @Size(min = 3, max = 50, message = "Modelo do caminhão inválido! (Deve ter entre 3 e 50 caracteres)")
    private String model;
    
    @NotNull
    private Integer year;
    
    @NotNull
    private Integer capacity;

    @NotNull
    private Double driverPercentage;
}