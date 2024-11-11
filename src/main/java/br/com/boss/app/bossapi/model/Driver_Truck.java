package br.com.boss.app.bossapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "driver_truck")
public class Driver_Truck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do motorista-caminhão (usado pelo banco)", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID da relação (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @NotNull
    @Schema(description = "Data de início do uso do caminhão pelo motorista", example = "2021-01-01")
    private Date startDate;

    @Schema(description = "Data de término do uso do caminhão pelo motorista", example = "2021-01-01")
    private Date endDate;

    @ManyToOne(cascade = CascadeType.PERSIST,
            targetEntity = Driver.class)
    @Schema(description = "ID do motorista", example = "1")
    @NotNull
    private Long driver;

    @ManyToOne(cascade = CascadeType.PERSIST,
            targetEntity = Truck.class)
    @Schema(description = "ID do caminhão", example = "1")
    @NotNull
    private Long truck;

    @OneToMany(mappedBy = "driver_truck",
            targetEntity = Delivery.class)
    @JsonIgnore
    private List<Delivery> deliveries;
}
