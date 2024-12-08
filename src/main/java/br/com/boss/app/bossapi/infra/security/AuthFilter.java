package br.com.boss.app.bossapi.infra.security;

import br.com.boss.app.bossapi.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Tag(name = "Filtro de autenticação", description = "Filtro de autenticação")
public class AuthFilter extends OncePerRequestFilter {
    private final TokenServiceJWT tokenService;
    private final AuthService authService;

    public AuthFilter(TokenServiceJWT tokenService, AuthService authService){
        this.tokenService = tokenService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null){
            String subject = this.tokenService.getSubject(token);

            UserDetails userDetails = this.authService.loadUserByUsername(subject);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null){
            return token.replace("Bearer ", "").trim();
        }

        return null;
    }

}
