package br.com.boss.app.bossapi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Tag(name = "Serviço de token JWT", description = "Serviço de token JWT")
public class TokenServiceJWT {

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return com.auth0.jwt.JWT.create()
                    .withIssuer("boss API")
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getAuthorities().stream().toList().getFirst().toString())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        }
        catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT: " + e);
        }
    }
    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return JWT.require(algorithm)
                    .withIssuer("boss API")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao verificar token JWT: " + e);
        }
    }
}
