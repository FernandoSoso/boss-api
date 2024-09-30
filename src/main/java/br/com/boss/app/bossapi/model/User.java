package br.com.boss.app.bossapi.model;

import br.com.boss.app.bossapi.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class User {
    private Long id;
    private String uuid;
    private String name;
    private String email;
    private String password;
    private Role role;
}
