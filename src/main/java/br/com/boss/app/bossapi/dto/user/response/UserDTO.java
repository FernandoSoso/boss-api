package br.com.boss.app.bossapi.dto.user.response;

import br.com.boss.app.bossapi.enums.Role;

public interface UserDTO {
    public String getUuid();
    public String getName();
    public String getEmail();
    public Role getRole();
}