package br.com.boss.app.bossapi.dto.user.response;

import br.com.boss.app.bossapi.enums.Role;

public record UserDTO(String id,
                      String name,
                      String email,
                      Role role) { }