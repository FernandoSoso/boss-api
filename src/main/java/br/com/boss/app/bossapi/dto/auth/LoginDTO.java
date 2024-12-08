package br.com.boss.app.bossapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotNull(message = "Email não pode ser nulo!")
        @NotBlank(message = "Email não pode ficar em branco!")
        @Email(message = "Email passado é inválido!")
        @Size(min = 3, max = 50, message = "Tamanho do email é inválido! (Deve estar entre 3 e 50 caracteres)")
        String email,

        @NotNull(message = "Senha não pode ser nula!")
        @NotBlank(message = "Senha não pode ficar em branco!")
        @Size(min = 8, max = 64, message = "Tamanho de senha inválido! (Deve estar entre 8 e 64 caracteres)")
        String password
) { }
