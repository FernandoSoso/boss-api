package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.truck.TruckDTO;
import br.com.boss.app.bossapi.dto.truck.UniqueTruckDTO;
import br.com.boss.app.bossapi.dto.truck.SubmitTruckDTO;

import br.com.boss.app.bossapi.model.Truck;
import br.com.boss.app.bossapi.service.TruckService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/truck")
@Tag(name = "Trucks", description = "Path relacionado a operações de caminhões")
public class TruckController {

    private final TruckService service;

    TruckController(TruckService service){
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Listar todos os caminhões", description = "Retorna uma lista com todos os caminhões cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caminhões encontrados", content = @Content(
                    schema = @Schema(implementation = TruckDTO.class)
            ))
    })
    public ResponseEntity<List<TruckDTO>> getTrucks() {
        List<TruckDTO> truckDTOList = service.getAll();
        return ResponseEntity.ok(truckDTOList);
    }

    @GetMapping("/unique/{uuid}")
    @Operation(summary = "Buscar caminhão por UUID", description = "Retorna um caminhão correspondente ao UUID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caminhão encontrado", content = @Content(
                    schema = @Schema(implementation = UniqueTruckDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Caminhão não encontrado")
    })
    public ResponseEntity<UniqueTruckDTO> uniqueTruck(@PathVariable String uuid) throws Exception {
        UniqueTruckDTO u = this.service.getUnique(uuid);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/submit")
    @Transactional
    @Operation(summary = "Cadastrar um novo caminhão", description = "Cria um novo caminhão e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caminhão cadastrado com sucesso", content = @Content(
                    schema = @Schema(implementation = SubmitTruckDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<SubmitTruckDTO> submitTruck(@RequestBody @Valid Truck newTruckData, UriComponentsBuilder uriBuilder) throws Exception {
        SubmitTruckDTO newTruck = this.service.insert(newTruckData);

        URI uri = uriBuilder.path("/user/unique/{uuid}").buildAndExpand(newTruckData.getUuid()).toUri();

        return ResponseEntity.created(uri).body(newTruck);
    }

    @PutMapping("/alter/{uuid}")
    @Transactional
    @Operation(summary = "Alterar um caminhão", description = "Altera um caminhão já cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caminhão alterado com sucesso", content = @Content(
                    schema = @Schema(implementation = SubmitTruckDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Caminhão não encontrado ou dados inválidos")
    })
    public ResponseEntity<SubmitTruckDTO> alterTruck(@PathVariable String uuid, @RequestBody @Valid Truck truckData) throws Exception {
        SubmitTruckDTO newTruck = this.service.update(truckData, uuid);

        return ResponseEntity.ok(newTruck);
    }

    @DeleteMapping("/{uuid}")
    @Transactional
    @Operation(summary = "Deletar um caminhão", description = "Deleta um caminhão cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Caminhão deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Caminhão não encontrado")
    })
    public ResponseEntity<Void> deleteTruck(@PathVariable String uuid) throws Exception {
        this.service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
