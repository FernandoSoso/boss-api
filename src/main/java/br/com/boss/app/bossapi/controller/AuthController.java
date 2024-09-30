package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.auth.LoginRequestDTO;
import br.com.boss.app.bossapi.dto.auth.LoginResponseDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest){
        //TODO: Implementar

        return null;
    }

    @GetMapping("/logout")
    public void logout(){
        //TODO: Ver necessidade
    }
}
