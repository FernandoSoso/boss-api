package br.com.boss.app.bossapi.service;

import br.com.boss.app.bossapi.dto.user.response.UserDTO;
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

    public User insert(User user) throws Exception {
        User u = this.repository.findByEmail(user.getEmail());
        if (u == null){
            String encriptedPassword = SenhaHash.getSHA256Hash(user.getPassword());

            user.setPassword(encriptedPassword);

            return this.repository.save(user);
        }
        else{
            throw new Exception("Email já cadastrado!");
        }
    }

    public User update (User user) throws Exception {
        User u = this.repository.findByEmail(user.getEmail());

        if (u != null){
            if (!Objects.equals(u.getUuid(), user.getUuid())){
                throw new Exception("Email já cadastrado!");
            }
        }
        else{
            u = this.repository.findByUuid(UUID.fromString(user.getUuid()));
        }

        // Se a senha não for a mesma, é criptografada a nova senha
        if (!u.getPassword().equals(user.getPassword())){
            user.setPassword(SenhaHash.getSHA256Hash(user.getPassword()));
        }

        // Atualização de propriedades
        u.setPassword(user.getPassword());
        u.setRole(user.getRole());
        u.setName(user.getName());
        u.setEmail(user.getEmail());

        return this.repository.save(u);
    }

    public void delete(String uuid){
        UUID formattedUuid = UUID.fromString(uuid);
        this.repository.deleteByUuid(formattedUuid);
    }

    public User getUnique(String uuid){
        UUID formattedUuid = UUID.fromString(uuid);
        return repository.findByUuid(formattedUuid);
    }

    public List<User> getAll(){
        List<User> users = repository.findAll();

        return repository.findAll();
    }
}
