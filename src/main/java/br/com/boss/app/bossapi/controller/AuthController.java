package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.auth.LoginDTO;
import br.com.boss.app.bossapi.dto.auth.TokenJWT;
import br.com.boss.app.bossapi.infra.security.TokenServiceJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Operações relacionadas a autenticação")
public class AuthController {
    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenService;

    public AuthController(AuthenticationManager manager, TokenServiceJWT tokenService){
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TokenJWT.class)
            )),
            @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos", content = @Content)
    })
    public ResponseEntity login(
            @Parameter(description = "Dados de autenticação") @RequestBody @Valid LoginDTO loginDTO){
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
            Authentication at = manager.authenticate(auth);

            User user = (User) at.getPrincipal();
            String token = tokenService.generateToken(user);

            return ResponseEntity.ok().body(new TokenJWT(token));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
