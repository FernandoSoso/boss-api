package br.com.boss.app.bossapi.dto.user;

import br.com.boss.app.bossapi.enums.UserRole;

public interface UniqueUserDTO {
    public String getUuid();
    public String getName();
    public String getEmail();
    public UserRole getUserRole();
}