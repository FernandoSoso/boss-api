package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da entrega (usado pelo banco)", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID da entrega (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @NotNull
    @NotBlank
    @Schema(description = "Origem da entrega", example = "São Paulo")
    private String origin;

    @NotNull
    @NotBlank
    @Schema(description = "Destino da entrega", example = "Rio de Janeiro")
    private String destination;

    @NotNull
    @Schema(description = "Valor por tonelada da carga (R$/ton)", example = "100.0")
    private Double valuePerTon;

    @NotNull
    @Schema(description = "Parte do motorista pela realização da entrega", example = "762.00")
    private Double driverShare;

    @NotNull
    @Schema(description = "Peso da carga (em toneladas)", example = "30.0")
    private Double weight;

    @NotBlank
    @Schema(description = "Observações sobre a entrega", example = "Carga frágil")
    private String observation;


    @Schema(description = "Valor líquido da entrega", example = "3000.0")
    private Double netValue;

    @Schema(description = "Valor bruto da entrega", example = "4000.0")
    private Double grossValue;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status da entrega", example = "PENDENTE")
    private DeliveryStatus status;

    @ManyToOne(cascade = CascadeType.PERSIST,
            targetEntity = Driver_Truck.class)
    @NotNull
    @Schema(description = "Quem realizou a entrega, com qual caminhão e em qual período", example = "1")
    private Driver_Truck driver_truck;
}