package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.SituationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "truck")
public class Truck {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do caminhão (usado pelo banco)", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID do caminhão (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @NotBlank
    @NotNull
    @Size(min = 7, max = 7, message = "Placa do caminhão inválida! (Deve ter 7 caracteres)")
    @Schema(description = "Placa do caminhão", example = "ABC1234")
    private String licensePlate;
    
    @NotBlank
    @Size(min = 3, max = 50, message = "Marca do caminhão inválida! (Deve ter entre 3 e 50 caracteres)")
    @Schema(description = "Marca do caminhão", example = "Volvo")
    private String brand;
    
    @NotBlank
    @Size(min = 3, max = 50, message = "Modelo do caminhão inválido! (Deve ter entre 3 e 50 caracteres)")
    @Schema(description = "Modelo do caminhão", example = "FH")
    private String model;
    
    @NotNull
    @Schema(description = "Ano de fabricação do caminhão", example = "2021")
    private Integer year;
    
    @NotNull
    @Schema(description = "Capacidade de carga do caminhão (em toneladas)", example = "30")
    private Integer capacity;

    @NotNull
    @Schema(description = "Porcentagem do motorista por viagem", example = "0.3")
    private Double driverPercentage;

    @NotNull
    @Schema(description = "Situação atual do caminhão", example = "DISPONIVEL")
    private SituationStatus truckStatus;

    @OneToMany(mappedBy = "truck",
            targetEntity = Driver_Truck.class)
    @Schema(description = "Histórico de motoristas do caminhão")
    private List<Driver_Truck> truckHistory;
}