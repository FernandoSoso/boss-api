package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private Role role;
}
