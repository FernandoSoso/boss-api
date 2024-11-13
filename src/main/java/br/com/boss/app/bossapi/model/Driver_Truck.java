package br.com.boss.app.bossapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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
@Schema(description = "Entidade que representa a relação entre motorista e caminhão")
public class Driver_Truck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do motorista-caminhão (usado pelo banco)", example = "1")
    @JsonIgnore
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID da relação (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID uuid;

    @NotNull(message = "A data de início é obrigatória")
    @Schema(description = "Data de início do uso do caminhão pelo motorista", example = "2021-01-01")
    private Date startDate;

    @Schema(description = "Data de término do uso do caminhão pelo motorista", example = "2021-01-01")
    private Date endDate;

    @ManyToOne(cascade = CascadeType.PERSIST,
            targetEntity = Driver.class)
    @Schema(description = "Motorista")
    @NotNull(message = "O motorista é obrigatório")
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(cascade = CascadeType.PERSIST,
            targetEntity = Truck.class)
    @Schema(description = "Caminhão")
    @NotNull(message = "O caminhão é obrigatório")
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @OneToMany(mappedBy = "driver_truck",
            targetEntity = Delivery.class)
    @JsonIgnore
    private List<Delivery> deliveries;
}
