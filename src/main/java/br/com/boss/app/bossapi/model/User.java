package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.UserRole;
import br.com.boss.app.bossapi.util.deserializer.UserRoleDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@ToString
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
@Schema(description = "Entidade que representa um usuário")
public class User {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Schema(description = "ID do usuário (usado pelo banco)", example = "1")
    @JsonIgnore
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID do usuário (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID uuid;

    @NotBlank(message = "Nome não pode ficar em branco!")
    @NotNull(message = "Nome não pode ser nulo!")
    @Size(min = 3, max = 50, message = "Tamanho do nome passado é inválido! (Deve estar entre 3 e 50 caracteres)")
    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String name;

    @NotNull(message = "Email não pode ser nulo!")
    @NotBlank(message = "Email não pode ficar em branco!")
    @Email(message = "Email passado é inválido!")
    @Size(min = 3, max = 50, message = "Tamanho do email é inválido! (Deve estar entre 3 e 50 caracteres)")
    @Schema(description = "Email do usuário", example = "joaosilva23@gmail.com")
    private String email;

    @NotBlank(message = "Senha não pode ficar em branco!")
    @NotNull(message = "Senha não pode ser nula!")
    @Size(min = 8, max = 64, message = "Tamanho de senha inválido! (Deve estar entre 8 e 64 caracteres)")
    @Schema(description = "Senha do usuário", example = "12345678")
    private String password;

    @NotNull(message = "Permissão do usuário não pode ser nula!")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Permissão do usuário", example = "USER")
    @JsonDeserialize(using = UserRoleDeserializer.class)
    private UserRole userRole;

    @Schema(description = "Se o dado se encontra disponível ou não", example = "true")
    @JsonIgnore
    private Boolean status;
}
