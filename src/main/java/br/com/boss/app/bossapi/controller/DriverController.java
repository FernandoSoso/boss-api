package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.driver.response.DriverDTO;
import br.com.boss.app.bossapi.dto.driver.request.SubmitDriverDTO;
import br.com.boss.app.bossapi.dto.driver.response.UniqueDriverDTO;
import jakarta.servlet.annotation.WebServlet;

import org.springframework.web.bind.annotation.*;


@WebServlet("/driver")
public class DriverController {

    //        String async = req.getParameter("async");
//        String cod = req.getParameter("cod");
//        String operacao = req.getParameter("operacao");
//        String offset = req.getParameter("offset");
//        String limit = req.getParameter("limit");

    @GetMapping("/")
    public DriverDTO getDrivers() {
        //TODO: Implementar

        return null;
    }

    @GetMapping("/unique/{id}")
    public UniqueDriverDTO uniqueDriver(@PathVariable Long id) {
        //TODO: Implementar
        return null;
    }

    @PostMapping("/submit")
    public void submitDriver(@RequestBody SubmitDriverDTO newDriverData) {
        //TODO: Implementar e ver necessidade das props antigas

//        String operacao = req.getParameter("operacao");
//        String codMotorista = req.getParameter("cod");
//        String nome = req.getParameter("nome");
//        String endereco = req.getParameter("endereco");
//        String telefonePrincipal = req.getParameter("telefonePrincipal");
//        String telefoneAlternativo = req.getParameter("telefoneAlternativo");
//        String telefoneAlternativo2 = req.getParameter("telefoneAlternativo2");
//        String codCaminhao = req.getParameter("caminhao");
    }

    @PutMapping("/alter/{id}")
    public void alterDriver(@PathVariable String id, @RequestBody SubmitDriverDTO driverData) {
        //TODO: Implementar
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable String id) {
        //TODO: Implementar
    }
}
