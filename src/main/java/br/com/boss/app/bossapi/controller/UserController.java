package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.user.request.SubmitUserDTO;
import br.com.boss.app.bossapi.dto.user.response.UniqueUserDTO;
import br.com.boss.app.bossapi.dto.user.response.UserDTO;
import br.com.boss.app.bossapi.model.User;
import br.com.boss.app.bossapi.repository.UserRepository;
import br.com.boss.app.bossapi.service.UserService;
import jakarta.servlet.annotation.WebServlet;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@WebServlet("/user")
public class UserController {
    //        String async = req.getParameter("async");
//        String cod = req.getParameter("cod");
//        String operacao = req.getParameter("operacao");
//        String offset = req.getParameter("offset");
//        String limit = req.getParameter("limit");
    private UserService service;

    UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = service.getAll();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/unique/{id}")
    public ResponseEntity<User> uniqueUser(@PathVariable String id) {
        User u = this.service.getUnique(id);

        return ResponseEntity.ok(u);
    }

    @PostMapping("/submit")
    public ResponseEntity<User> submitUser(@RequestBody @Valid User newUserData, UriComponentsBuilder uriBuilder) {
        this.service.insert(newUserData);

        URI uri = uriBuilder.path("/user/unique/{id}").buildAndExpand(newUserData.getUuid()).toUri();

        return ResponseEntity.created(uri).body(newUserData);
    }

    @PutMapping("/alter/{id}")
    public ResponseEntity<User> alterUser(@PathVariable String id, @RequestBody User userData) {
        this.service.update(userData);

        return ResponseEntity.ok(userData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
