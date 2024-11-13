package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.dto.user.UniqueUserDTO;
import br.com.boss.app.bossapi.dto.user.UserDTO;
import br.com.boss.app.bossapi.enums.UserRole;
import br.com.boss.app.bossapi.model.User;
import br.com.boss.app.bossapi.repository.UserRepository;
import br.com.boss.app.bossapi.util.HashPassword;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public UniqueUserDTO insert(User submittedUser) throws Exception {
        User u = this.repository.findByEmail(submittedUser.getEmail());

        if (u == null){
            String encriptedPassword = HashPassword.getSHA256Hash(submittedUser.getPassword());

            System.out.println(submittedUser.getUserRole());
            submittedUser.setPassword(encriptedPassword);

            return persist(submittedUser);
        }
        else{
            throw new BadRequestException("Email já cadastrado!");
        }
    }

    public UniqueUserDTO update (User toUpdateUser, String userId) throws Exception {
        toUpdateUser.setUuid(UUID.fromString(userId));
        User u = this.repository.findByUuid(UUID.fromString(userId));

        if (u == null){
            throw new BadRequestException("Usuário não encontrado!");
        }
        else{
            u = this.repository.findByEmail(toUpdateUser.getEmail());

            if (!Objects.equals(u.getUuid().toString(), userId)){
                throw new BadRequestException("Email já cadastrado!");
            }
        }

        // Se a senha não for a mesma, é criptografada a nova senha
        if (!u.getPassword().equals(toUpdateUser.getPassword())){
            toUpdateUser.setPassword(HashPassword.getSHA256Hash(toUpdateUser.getPassword()));
        }

        return persist(toUpdateUser);
    }

    private UniqueUserDTO persist(User u){
        u.setStatus(true);
        this.repository.save(u);

        return getUniqueUserDTO(u);
    }


    public void delete(String uuid) throws Exception {
        User u = this.repository.findByUuid(UUID.fromString(uuid));

        if (u == null){
            throw new BadRequestException("Usuário não encontrado!");
        }

        u.setStatus(false);

        this.repository.save(u);
    }

    public UniqueUserDTO getUnique(String uuid) throws Exception {
        User u = this.repository.findByUuid(UUID.fromString(uuid));

        if (u == null){
            throw new BadRequestException("Usuário não encontrado!");
        }
        else{
            return getUniqueUserDTO(u);
        }
    }

    public List<UserDTO> getAll(){
       return repository.getAllUsers();
    }

    private UniqueUserDTO getUniqueUserDTO(User u) {
        return new UniqueUserDTO() {
            @Override
            public String getName() {return u.getName();}
            @Override
            public String getEmail() {return u.getEmail();}
            @Override
            public UserRole getUserRole() {return u.getUserRole();}
            @Override
            public String getUuid() {return u.getUuid().toString();}
        };
    }
}
