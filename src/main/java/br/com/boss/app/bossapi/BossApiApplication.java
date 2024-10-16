package br.com.boss.app.bossapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "boss API",
                version = "1.0",
                description = "Documentação da API boss",
                contact = @Contact(name = "Suporte", email = "suporte@exemplo.com")
        )
)
@SpringBootApplication
public class BossApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BossApiApplication.class, args);
    }

}
