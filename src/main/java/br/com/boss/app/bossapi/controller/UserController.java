package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.user.request.SubmitUserDTO;
import br.com.boss.app.bossapi.dto.user.response.UniqueUserDTO;
import br.com.boss.app.bossapi.dto.user.response.UserDTO;
import jakarta.servlet.annotation.WebServlet;

import org.springframework.web.bind.annotation.*;

@WebServlet("/user")
public class UserController {
    //        String async = req.getParameter("async");
//        String cod = req.getParameter("cod");
//        String operacao = req.getParameter("operacao");
//        String offset = req.getParameter("offset");
//        String limit = req.getParameter("limit");

    @GetMapping("/")
    public UserDTO getUsers() {
        //TODO: Implementar

        return null;
    }

    @GetMapping("/unique/{id}")
    public UniqueUserDTO uniqueUser(@PathVariable Long id) {
        //TODO: Implementar
        return null;
    }

    @PostMapping("/submit")
    public void submitUser(@RequestBody SubmitUserDTO newUserData) {
        //TODO: Implementar e ver necessidade das props antigas

//        String nome = req.getParameter("nome");
//        String email = req.getParameter("email");
//        String senha = req.getParameter("senha");
//        String permissao = req.getParameter("permissao");
//        String operacao = req.getParameter("operacao");
//        String codExterno = req.getParameter("cod");
    }

    @PutMapping("/alter/{id}")
    public void alterUser(@PathVariable String id, @RequestBody SubmitUserDTO userData) {
        //TODO: Implementar
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        //TODO: Implementar
    }
}
