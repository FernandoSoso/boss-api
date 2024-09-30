package br.com.boss.app.bossapi.dto.user.request;

import br.com.boss.app.bossapi.enums.Role;

public record SubmitUserDTO(String name,
                            String email,
                            String password,
                            Role role) { }
