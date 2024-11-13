package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.delivery.DeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.InputDeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.SubmitDeliveryDTO;
import br.com.boss.app.bossapi.dto.delivery.UniqueDeliveryDTO;

import br.com.boss.app.bossapi.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/delivery")
@Tag(name = "Delivery", description = "Path relacionado a operações de entregas")
public class DeliveryController {

    private final DeliveryService service;

    DeliveryController(DeliveryService service){
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Listar entregas", description = "Retorna uma lista com todas as entregas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entregas encontradas", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DeliveryDTO.class)
            ))
    })
    public ResponseEntity<List<DeliveryDTO>> getDeliveries() {
        List<DeliveryDTO> deliveries = this.service.getAll();

        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/unique/{uuid}")
    @Operation(summary = "Buscar uma entrega", description = "Retorna uma entrega correspondente ao UUID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrega encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UniqueDeliveryDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada", content = @Content)
    })
    public ResponseEntity<UniqueDeliveryDTO> uniqueDelivery(
            @Parameter(description = "Uuid da entrega a ser retornada") @PathVariable String uuid
    ) throws Exception {
        UniqueDeliveryDTO delivery = this.service.getUnique(uuid);

        if (delivery == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(delivery);
        }
    }

    @PostMapping("/submit")
    @Transactional
    @Operation(summary = "Cadastra uma entrega", description = "Submete o cadastro de uma entrega ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrega cadastrada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SubmitDeliveryDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos", content = @Content)
    })
    public ResponseEntity<SubmitDeliveryDTO> submitDelivery(
            @Parameter(description = "Dados da entrega a ser cadastrada") @RequestBody @Valid InputDeliveryDTO newDeliveryData,
            UriComponentsBuilder uriBuilder
    ) throws Exception {
        SubmitDeliveryDTO delivery = this.service.insert(newDeliveryData);

        URI uri = uriBuilder.path("/delivery/unique/{uuid}").buildAndExpand(delivery.getUuid()).toUri();

        return ResponseEntity.created(uri).body(delivery);
    }

    @PutMapping("/alter/{uuid}")
    @Transactional
    @Operation(summary = "Alterar uma entrega", description = "Altera uma entrega do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrega alterada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SubmitDeliveryDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada", content = @Content)
    })
    public ResponseEntity<SubmitDeliveryDTO> alterDelivery(
            @Parameter(description = "Uuid da entrega a ser alterada") @PathVariable String uuid,
            @Parameter(description = "Dados a serem alterados da entrega") @RequestBody @Valid InputDeliveryDTO deliveryData
    ) throws Exception {
        SubmitDeliveryDTO delivery = this.service.update(deliveryData, uuid);

        if (delivery == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(delivery);
        }
    }

    @DeleteMapping("/{uuid}")
    @Transactional
    @Operation(summary = "Deletar uma entrega", description = "Deleta uma entrega do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrega deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "Entrega não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deleteDelivery(
            @Parameter(description = "Uuid da entrega a ser deletada") @PathVariable String uuid
    ) throws Exception {
        if (this.service.delete(uuid)) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
