package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.driver_truck.EntryDTO;
import br.com.boss.app.bossapi.dto.driver_truck.Driver_TruckDTO;
import br.com.boss.app.bossapi.service.DriverTruckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@Tag(name = "History", description = "Path relacionado a operações de histórico de motoristas e caminhões")
public class DriverTruckController {

    private final DriverTruckService service;

    DriverTruckController(DriverTruckService service){
        this.service = service;
    }

    @GetMapping("/truck/{uuid}")
    @Operation(summary = "Retorna o histórico de um caminhão", description = "Retorna o histórico de um caminhão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico encontrado"),
            @ApiResponse(responseCode = "404", description = "Caminhão não encontrado")
    })
    public ResponseEntity<List<Driver_TruckDTO>> getTruckHistory(
            @Parameter(description = "Uuid do caminhão a ter seu histórico retornado") @PathVariable String uuid
    ){
        if (service.getTruckHistory(uuid) == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(service.getTruckHistory(uuid));
        }
    }

    @GetMapping("/driver/{uuid}")
    @Operation(summary = "Retorna o histórico de um motorista", description = "Retorna o histórico de um motorista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico encontrado"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado")
    })
    public ResponseEntity<List<Driver_TruckDTO>> getDriverHistory(
            @Parameter(description = "Uuid do motorista a ter seu histórico retornado") @PathVariable String uuid
    ){
        List<Driver_TruckDTO> driverHistory = service.getDriverHistory(uuid);

        if (driverHistory == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(driverHistory);
        }
    }

    @PostMapping("/change")
    @Transactional
    @Operation(summary = "Altera uma entrada de um motorista/caminhão", description = "Altera uma entrada no histórico de um motorista ou caminhão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrada alterada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Entrada não alterada"),
            @ApiResponse(responseCode = "400", description = "Dados forncecidos são inválidos")
    })
    public ResponseEntity<Void> changeEntry(
            @Parameter(description = "Uuids do caminhão e motorista a ser adicionado na entrada atual") @RequestBody @Valid EntryDTO newEntryDTO
    ) throws Exception {
        boolean isCreated = this.service.changeHistory(newEntryDTO);

        if (isCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/setEnd/{uuid}")
    @Transactional
    @Operation(summary = "Finaliza uma entrada de um motorista/caminhão", description = "Finaliza  uma entrada no histórico de um motorista ou caminhão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrada finalizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entrada não encontrada")
    })
    public ResponseEntity<Void> endHistory(
            @Parameter(description = "Uuid da entrada a ser terminada") @PathVariable String uuid
    ) throws Exception {

        if(this.service.endHistory(uuid)){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}