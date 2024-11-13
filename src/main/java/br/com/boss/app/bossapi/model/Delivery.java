package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.DeliveryStatus;
import br.com.boss.app.bossapi.util.deserializer.DeliveryStatusDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
@Schema(description = "Entidade que representa uma entrega no sistema")
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID da entrega (usado pelo banco)", example = "1")
    @JsonIgnore
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID da entrega (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID uuid;

    @NotNull(message = "Local de origem não pode ser nulo!")
    @NotBlank(message = "Local de origem não pode ser vazio!")
    @Schema(description = "Origem da entrega", example = "São Paulo")
    @Size(min = 3, max = 255, message = "Origem da entrega inválida! (Deve ter entre 3 e 255 caracteres)")
    private String origin;

    @NotNull(message = "Destino não pode ser nulo!")
    @NotBlank(message = "Destino não pode ser vazio!")
    @Schema(description = "Destino da entrega", example = "Rio de Janeiro")
    @Size(min = 3, max = 255, message = "Destino da entrega inválido! (Deve ter entre 3 e 255 caracteres)")
    private String destination;

    @NotNull(message = "Valor por tonelada não pode ser nulo!")
    @Schema(description = "Valor por tonelada da carga (R$/ton)", example = "100.0")
    @DecimalMin(value = "0.0", message = "Valor por tonelada inválido! (Deve ser maior que 0)")
    @DecimalMax(value = "9999999999.99", message = "Valor por tonelada inválido! (Deve ser menor que 9.999.999.999,99)")
    private Double valuePerTon;

    @NotNull(message = "Parte do motorista não pode ser nula!")
    @Schema(description = "Parte do motorista pela realização da entrega", example = "762.00")
    @DecimalMin(value = "0.0", message = "Parte do motorista inválida! (Deve ser maior que 0)")
    @DecimalMax(value = "9999999999.99", message = "Parte do motorista inválida! (Deve ser menor que 9.999.999.999,99)")
    private Double driverShare;

    @NotNull(message = "Peso da carga não pode ser nulo!")
    @Schema(description = "Peso da carga (em toneladas)", example = "30.0")
    @DecimalMin(value = "0.0", message = "Peso da carga inválido! (Deve ser maior que 0)")
    @DecimalMax(value = "9999999999.0", message = "Peso da carga inválido! (Deve ser menor que 9.999.999.999,99)")
    private Double weight;

    @Schema(description = "Observações sobre a entrega", example = "Carga frágil")
    @Size(max = 255, message = "Observações inválidas! (Deve ter no máximo 255 caracteres)")
    private String observation;

    @Schema(description = "Valor líquido da entrega", example = "3000.0")
    @DecimalMin(value = "0.0", message = "Valor líquido inválido! (Deve ser maior que 0)")
    @DecimalMax(value = "9999999999.99", message = "Valor líquido inválido! (Deve ser menor que 9.999.999.999,99)")
    private Double netValue;

    @Schema(description = "Valor bruto da entrega", example = "4000.0")
    @DecimalMin(value = "0.0", message = "Valor bruto inválido! (Deve ser maior que 0)")
    @DecimalMax(value = "9999999999.99", message = "Valor bruto inválido! (Deve ser menor que 9.999.999.999,99)")
    private Double grossValue;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status da entrega", example = "PENDENTE")
    @JsonDeserialize(using = DeliveryStatusDeserializer.class)
    private DeliveryStatus deliveryStatus;

    @ManyToOne(targetEntity = Driver_Truck.class)
    @NotNull(message = "Relação entre o motorista e o caminhão não pode ser nula!")
    @Schema(description = "Quem realizou a entrega, com qual caminhão e em qual período", example = "1")
    private Driver_Truck driver_truck;

    @Schema(description = "Se o dado se encontra disponível ou não", example = "true")
    @JsonIgnore
    private Boolean status;
}