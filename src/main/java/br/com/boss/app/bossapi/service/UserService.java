package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.dto.user.request.SubmitUserDTO;
import br.com.boss.app.bossapi.dto.user.response.UniqueUserDTO;
import br.com.boss.app.bossapi.dto.user.response.UserDTO;
import br.com.boss.app.bossapi.enums.Role;
import br.com.boss.app.bossapi.model.User;
import br.com.boss.app.bossapi.repository.UserRepository;
import br.com.boss.app.bossapi.util.SenhaHash;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public void insert(SubmitUserDTO user) throws Exception {
        User u = this.repository.findByEmail(user.getEmail());

        if (u == null){
            String encriptedPassword = SenhaHash.getSHA256Hash(user.getPassword());

            u = new User(
                user.getEmail(),
                encriptedPassword,
                user.getName(),
                user.getRole()
            );

            this.repository.save(u);
        }
        else{
            throw new Exception("Email já cadastrado!");
        }
    }

    public void update (SubmitUserDTO user, String userId) throws Exception {
        User u = this.repository.findByEmail(user.getEmail());

        if (u != null){
            if (!Objects.equals(u.getUuid(), userId)){
                throw new Exception("Email já cadastrado!");
            }
        }
        else{
            u = this.repository.findByUuid(userId);
        }

        // Se a senha não for a mesma, é criptografada a nova senha
        if (!u.getPassword().equals(user.getPassword())){
            u.setPassword(SenhaHash.getSHA256Hash(user.getPassword()));
        }

        // Atualização de propriedades
        u.setPassword(user.getPassword());
        u.setRole(user.getRole());
        u.setName(user.getName());
        u.setEmail(user.getEmail());

        this.repository.save(u);
    }

    public void delete(String uuid){
        this.repository.deleteByUuid(uuid);
    }

    public UniqueUserDTO getUnique(String uuid){
        User u = this.repository.findByUuid(uuid);

        return new UniqueUserDTO() {
            @Override
            public String getName() {return u.getName();}
            @Override
            public String getEmail() {return u.getEmail();}
            @Override
            public Role getRole() {return u.getRole();}
            @Override
            public String getUuid() {return u.getUuid();}
        };
    }

    public List<UserDTO> getAll(){
       return repository.findAllUsers();
    }
}
