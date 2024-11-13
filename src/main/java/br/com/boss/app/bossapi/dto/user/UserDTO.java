package br.com.boss.app.bossapi.dto.user;

import br.com.boss.app.bossapi.enums.UserRole;

public interface UserDTO {
    String getUuid();
    String getName();
    String getEmail();
    UserRole getUserRole();
}