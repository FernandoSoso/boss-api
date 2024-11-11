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
@Table(name = "\"user\"", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Schema(description = "Entidade que representa um usuário")
public class User {

    @Id @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Schema(description = "ID do usuário (usado pelo banco)", example = "1")
    private Long id;

    @UuidGenerator
    @Schema(description = "UUID do usuário (passado pelas requisições)", example = "123e4567-e89b-12d3-a456-426614174000")
    private String uuid;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 50, message = "Tamanho do nome passa é inválido! (Deve estar entre 3 e 50 caracteres)")
    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String name;

    @NotNull
    @NotBlank
    @Email(message = "Email passado é inválido!")
    @Size(min = 3, max = 50, message = "Tamanho do email é inválido! (Deve estar entre 3 e 50 caracteres)")
    @Schema(description = "Email do usuário", example = "joaosilva23@gmail.com")
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 8, max = 64, message = "Tamanho de senha inválido! (Deve estar entre 8 e 64 caracteres)")
    @Schema(description = "Senha do usuário", example = "12345678")
    private String password;

    @NotNull
    @NotBlank
    @Schema(description = "Permissão do usuário", example = "USER")
    private Role role;

    public User(String email, String encriptedPassword, String name, Role role) {
        this.email = email;
        this.password = encriptedPassword;
        this.name = name;
        this.role = role;
    }
}
