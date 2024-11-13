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
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public UniqueUserDTO insert(User submittedUser) throws Exception {
        submittedUser.setPassword(HashPassword.getSHA256Hash(submittedUser.getPassword()));

        return persist(submittedUser);
    }

    public UniqueUserDTO update (User userData, String userId) throws Exception {
        User u = this.repository.findByUuid(UUID.fromString(userId));

        if (u == null){
            return null;
        }

        u.setName(userData.getName());
        u.setEmail(userData.getEmail());
        u.setUserRole(userData.getUserRole());
        u.setPassword(HashPassword.getSHA256Hash(userData.getPassword()));

        return persist(u);
    }

    private UniqueUserDTO persist(User userToPersist) throws Exception {
        User u = this.repository.findByEmail(userToPersist.getEmail());

        if (u != null){
            if (!u.getUuid().equals(userToPersist.getUuid())){
                throw new BadRequestException("Email já cadastrado!");
            }
        }

        userToPersist.setStatus(true);
        this.repository.save(userToPersist);

        return getUniqueUserDTO(userToPersist);
    }


    public boolean delete(String uuid) {
        User u = this.repository.findByUuid(UUID.fromString(uuid));

        if (u == null){
            return false;
        }

        u.setStatus(false);

        this.repository.save(u);
        return true;
    }

    public UniqueUserDTO getUnique(String uuid) {
        User u = this.repository.findByUuid(UUID.fromString(uuid));

        if (u == null){
            return null;
        }

        return getUniqueUserDTO(u);
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
