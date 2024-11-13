package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.SituationStatus;
import br.com.boss.app.bossapi.util.deserializer.SituationStatusDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "truck")
@Schema(description = "Entidade que representa um caminhão no sistema")
public class Truck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do caminhão (usado pelo banco)", example = "1")
    @JsonIgnore
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID do caminhão (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID uuid;

    @NotNull(message = "Placa do caminhão não pode ser nula")
    @Schema(description = "Placa do caminhão", example = "ABC1234")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$", message = "Placa do caminhão inválida! (Formato deve ser ABC1234 ou ABC1D23)")
    private String licensePlate;

    @NotBlank(message = "Marca do caminhão não pode estar em branco")
    @Size(min = 3, max = 50, message = "Marca do caminhão inválida! (Deve ter entre 3 e 50 caracteres)")
    @Schema(description = "Marca do caminhão", example = "Volvo")
    private String brand;
    
    @NotBlank(message = "Modelo do caminhão não pode estar em branco")
    @Size(min = 3, max = 50, message = "Modelo do caminhão inválido! (Deve ter entre 3 e 50 caracteres)")
    @Schema(description = "Modelo do caminhão", example = "FH")
    private String model;
    
    @NotNull(message = "Ano de fabricação do caminhão não pode ser nulo")
    @Schema(description = "Ano de fabricação do caminhão", example = "2021")
    @Min(value = 1900, message = "Ano de fabricação inválido! (Deve ser maior que 1900)")
    @Max(value = 2999, message = "Ano de fabricação inválido! (Deve ser menor que 9999)")
    private Integer year;
    
    @NotNull(message = "Capacidade de carga do caminhão não pode ser nula")
    @Schema(description = "Capacidade de carga do caminhão (em toneladas)", example = "30")
    @DecimalMin(value = "0.0", message = "Capacidade de carga inválida! (Deve ser maior que 0)")
    @DecimalMax(value = "99999999.0", message = "Capacidade de carga inválida! (Deve ser menor que 99.999.999)")
    private Double capacity;

    @NotNull(message = "Porcentagem do motorista não pode ser nula")
    @Schema(description = "Porcentagem do motorista por viagem", example = "0.3")
    @DecimalMin(value = "0.0", message = "Porcentagem do motorista inválida! (Deve ser maior que 0)")
    @DecimalMax(value = "100.0", inclusive = true , message = "Porcentagem do motorista inválida! (Deve ser menor que 100%)")
    private Double driverPercentage;

    @NotNull(message = "Situação do caminhão não pode ser nula")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Situação atual do caminhão", example = "DISPONIVEL")
    @JsonDeserialize(using = SituationStatusDeserializer.class)
    private SituationStatus truckStatus;

    @OneToMany(mappedBy = "truck",
            targetEntity = Driver_Truck.class)
    @Schema(description = "Histórico de motoristas do caminhão")
    @JsonIgnore
    private List<Driver_Truck> truckHistory;

    @Schema(description = "Se o dado se encontra disponível ou não", example = "true")
    @JsonIgnore
    private Boolean status;
}