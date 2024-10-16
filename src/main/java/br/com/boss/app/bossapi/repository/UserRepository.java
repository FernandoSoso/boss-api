package br.com.boss.app.bossapi.repository;

import br.com.boss.app.bossapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    public User findByUuid(UUID uuid);
    public void deleteByUuid(UUID uuid);

}
