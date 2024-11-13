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
        SELECT
        u.id,
        u.uuid,
        u.email,
        u.password,
        u.name,
        u.user_role,
        u.status
        FROM "user" AS u
        WHERE u.email = :email AND u.status = true
    """, nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Query(value = """
        SELECT
        u.id,
        u.uuid,
        u.email,
        u.password,
        u.name,
        u.user_role,
        u.status
        FROM "user" AS u
        WHERE u.uuid = :uuid AND u.status = true
    """, nativeQuery = true)
    User findByUuid(@Param("uuid") UUID uuid);

    @Query(value = """
        SELECT
        u.uuid,
        u.email,
        u.name,
        u.user_role
        FROM "user" AS u
        WHERE u.status = true
   """, nativeQuery = true)
    List<UserDTO> getAllUsers();
}
