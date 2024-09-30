package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.truck.response.TruckDTO;
import br.com.boss.app.bossapi.dto.truck.response.UniqueTruckDTO;
import br.com.boss.app.bossapi.dto.truck.request.SubmitTruckDTO;

import org.springframework.web.bind.annotation.*;

@RestController("/truck")
public class TruckController {
//        String async = req.getParameter("async");
//        String cod = req.getParameter("cod");
//        String operacao = req.getParameter("operacao");
//        String offset = req.getParameter("offset");
//        String limit = req.getParameter("limit");

    @GetMapping("/")
    public TruckDTO getTrucks() {
        //TODO: Implementar
        return null;
    }

    @GetMapping("/unique/{id}")
    public UniqueTruckDTO uniqueTruck(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/submit")
    public void submitTruck(@RequestBody SubmitTruckDTO newTruckData) {
        //TODO: Implementar e ver necessidade das props antigas

//        String operacao = req.getParameter("operacao");
//        String codCaminhao = req.getParameter("cod");
//        String placa = req.getParameter("placa");
//        String modelo = req.getParameter("modelo");
//        String marca = req.getParameter("marca");
//        String ano = req.getParameter("ano");
//        String capacidade = req.getParameter("capacidade");
//        String percentualMotorista = req.getParameter("percentualMotorista");
//        String codMotorista = req.getParameter("motorista");
    }

    @PutMapping("/alter/{id}")
    public void alterTruck(@PathVariable String id, @RequestBody SubmitTruckDTO truckData) {
        //TODO: Implementar
    }

    @DeleteMapping("/{id}")
    public void deleteTruck(@PathVariable String id) {
        //TODO: Implementar
    }
}
