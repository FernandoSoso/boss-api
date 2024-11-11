package br.com.boss.app.bossapi.repository;

import br.com.boss.app.bossapi.dto.user.response.UniqueUserDTO;
import br.com.boss.app.bossapi.dto.user.response.UserDTO;
import br.com.boss.app.bossapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUuid(String uuid);
    void deleteByUuid(String uuid);
    List<UserDTO> findAllUsers();
}
