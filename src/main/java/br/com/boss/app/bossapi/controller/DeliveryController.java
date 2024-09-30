package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.delivery.response.DeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.request.SubmitDeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.response.UniqueDeliveryDTO;
import jakarta.servlet.annotation.WebServlet;

import org.springframework.web.bind.annotation.*;

@WebServlet("/delivery")
public class DeliveryController {
//        String async = req.getParameter("async");
//        String cod = req.getParameter("cod");
//        String operacao = req.getParameter("operacao");
//        String offset = req.getParameter("offset");
//        String limit = req.getParameter("limit");

    @GetMapping("/")
    public DeliveryDTO getDeliveries() {
        //TODO: Implementar

        return null;
    }

    @GetMapping("/unique/{id}")
    public UniqueDeliveryDTO uniqueDelivery(@PathVariable Long id) {
        //TODO: Implementar
        return null;
    }

    @PostMapping("/submit")
    public void submitDelivery(@RequestBody SubmitDeliveryDTO newDeliveryData) {
        //TODO: Implementar e ver necessidade das props antigas

//        String operacao = req.getParameter("operacao");
//        String codFrete = req.getParameter("cod");
//        String origem = req.getParameter("origem");
//        String destino = req.getParameter("destino");
//        String valorTonelada = req.getParameter("valorTonelada");
//        String peso = req.getParameter("peso");
//        String observacao = req.getParameter("observacao");
//        String estado = req.getParameter("estado");
//        String codMotorista = req.getParameter("motorista");
//        String codCaminhao = req.getParameter("caminhao");
    }

    @PutMapping("/alter/{id}")
    public void alterDelivery(@PathVariable String id, @RequestBody SubmitDeliveryDTO deliveryData) {
        //TODO: Implementar
    }

    @DeleteMapping("/{id}")
    public void deleteDelivery(@PathVariable String id) {
        //TODO: Implementar
    }
}
