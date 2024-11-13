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

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "driver")
@Schema(description = "Entidade que representa um motorista no sistema")
public class Driver {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do motorista (usado pelo banco)", example = "1")
    @JsonIgnore
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID do motorista (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID uuid;

    @NotNull(message = "Nome não pode ser nulo!")
    @NotBlank(message = "Nome não pode estar em branco!")
    @Schema(description = "Nome do motorista", example = "João da Silva")
    @Size(min = 3, max = 255, message = "Tamanho do nome passado é inválido! (Deve estar entre 3 e 255 caracteres)")
    private String name;

    @NotNull(message = "Telefone primário não pode ser nulo!")
    @NotBlank(message = "Telefone primário não pode estar em branco!")
    @Schema(description = "Telefone primário do motorista", example = "(99) 99999-9999")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$|^\\(\\d{2}\\) \\d{4}-\\d{4}$", message = "Telefone inválido! (Deve estar no formato (99) 99999-9999 ou (99) 9999-9999)")
    private String primaryPhone;

    @Schema(description = "Telefone secundário do motorista", example = "(99) 99999-9999")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$|^\\(\\d{2}\\) \\d{4}-\\d{4}$", message = "Telefone inválido! (Deve estar no formato (99) 99999-9999 ou (99) 9999-9999)")
    private String secondaryPhone;

    @Email(message = "Email inválido")
    @NotBlank(message = "Email não pode estar em branco!")
    @Schema(description = "Email do motorista", example = "joaosilva23@gmail.com")
    private String email;

    @NotNull(message = "Número da CNH não pode ser nula!")
    @NotBlank(message = "Número da CNH não pode estar em branco!")
    @Schema(description = "Número da CNH do motorista", example = "12345678901")
    @Pattern(regexp = "[0-9]{11}", message = "CNH inválida! (Deve ter 11 dígitos)")
    private String licenseNumber;

    @NotNull(message = "Data de vencimento da CNH não pode ser nula!")
    @Schema(description = "Data de vencimento da CNH do motorista", example = "2021-01-01")
    @Future(message = "Data de vencimento inválida! (Deve ser uma data futura)")
    private Date licenseExpirationDate;

    @OneToMany(mappedBy = "driver",
            targetEntity = Driver_Truck.class)
    @JsonIgnore
    private List<Driver_Truck> driverHistory;

    @NotNull(message = "Situação do motorista não pode ser nula!")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Situação atual do motorista", example = "DISPONIVEL")
    @JsonDeserialize(using = SituationStatusDeserializer.class)
    private SituationStatus driverStatus;

    @Schema(description = "Se o dado se encontra disponível ou não", example = "true")
    @JsonIgnore
    private Boolean status;
}