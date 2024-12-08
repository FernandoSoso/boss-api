package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Tag(name = "Serviço de autenticação", description = "Serviço de autenticação")
public class AuthService implements UserDetailsService {

    private final UserRepository repository;

    public AuthService(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        br.com.boss.app.bossapi.model.User user = this.repository.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException("Email ou senha incorreto!");
        }
        else {
            UserDetails userDetails = User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .authorities(user.getUserRole().toString()).build();

            return userDetails;
        }
    }
}
