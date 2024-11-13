package br.com.boss.app.bossapi.repository;

import br.com.boss.app.bossapi.dto.user.UserDTO;
import br.com.boss.app.bossapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        SELECT\s
        u.id,\s
        u.uuid,\s
        u.email,\s
        u.password,\s
        u.name,\s
        u.user_role,\s
        u.status\s
        FROM "user" AS u
        WHERE u.email = :email AND u.status = true
        \s""", nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Query(value = """
        SELECT\s
        u.id,\s
        u.uuid,\s
        u.email,\s
        u.password,\s
        u.name,\s
        u.user_role,\s
        u.status\s
        FROM "user" AS u
        WHERE u.uuid = :uuid AND u.status = true
        \s""", nativeQuery = true)
    User findByUuid(@Param("uuid") UUID uuid);

    @Query(value = """
        SELECT\s
        u.uuid,\s
        u.email,\s
        u.name,\s
        u.user_role\s
        FROM "user" AS u
        WHERE u.status = true
   \s""", nativeQuery = true)
    List<UserDTO> getAllUsers();
}
