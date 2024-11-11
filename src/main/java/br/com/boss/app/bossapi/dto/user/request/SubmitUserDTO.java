package br.com.boss.app.bossapi.dto.user.request;

import br.com.boss.app.bossapi.enums.Role;
import br.com.boss.app.bossapi.model.User;

public interface SubmitUserDTO {
    public String getName();
    public String getEmail();
    public String getPassword();
    public Role getRole();

    Object getUuid();
}
