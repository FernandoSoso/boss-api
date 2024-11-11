package br.com.boss.app.bossapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "driver", uniqueConstraints = {
    @UniqueConstraint(columnNames = "licenseNumber"),
    @UniqueConstraint(columnNames = "email")
})
public class Driver {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do motorista (usado pelo banco)", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID do motorista (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @NotNull
    @NotBlank
    @Schema(description = "Nome do motorista", example = "João da Silva")
    private String name;

    @NotNull
    @NotBlank
    @Schema(description = "Telefone primário do motorista", example = "(99) 99999-9999")
    private String primaryPhone;

    @Schema(description = "Telefone secundário do motorista", example = "(99) 99999-9999")
    private String secondaryPhone;

    @Schema(description = "Email do motorista", example = "joaosilva23@gmail.com")
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Schema(description = "Número da CNH do motorista", example = "12345678901")
    private String licenseNumber;

    @NotNull
    @NotBlank
    @Schema(description = "Data de vencimento da CNH do motorista", example = "2021-01-01")
    private Date licenseExpirationDate;

    @OneToMany(mappedBy = "driver",
            targetEntity = Driver_Truck.class)
    @JsonIgnore
    private List<Driver_Truck> driverHistory;
}