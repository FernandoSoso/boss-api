package br.com.boss.app.bossapi.dto.user.response;

import br.com.boss.app.bossapi.enums.Role;

public record UniqueUserDTO(String id,
                            String name,
                            String email,
                            Role role) { }