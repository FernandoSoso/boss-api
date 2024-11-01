package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.ValueGenerationType;

@ToString
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "\"user\"", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Schema(description = "Entidade que representa um usuário")
public class User {

    @Schema(description = "ID do usuário (usado pelo banco)", example = "1")
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Schema(description = "UUID do usuário (passado pelas requisições)")
    @UuidGenerator
    private String uuid;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50, message = "Tamanho do nome passa é inválido! (Deve estar entre 3 e 50 caracteres)")
    private String name;

    @NotNull
    @NotBlank
    @Email(message = "Email passado é inválido!")
    @Size(min = 3, max = 50, message = "Tamanho do email é inválido! (Deve estar entre 3 e 50 caracteres)")
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 8, max = 64, message = "Tamanho de senha inválido! (Deve estar entre 8 e 64 caracteres)")
    private String password;

    @NotNull
    @NotBlank
    private Role role;
}
