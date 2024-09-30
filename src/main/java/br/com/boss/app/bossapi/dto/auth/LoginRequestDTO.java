package br.com.boss.app.bossapi.dto.auth;

public record LoginRequestDTO(String email,
                              String password) { }