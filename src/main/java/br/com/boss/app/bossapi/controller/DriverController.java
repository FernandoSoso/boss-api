package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.driver.DriverDTO;
import br.com.boss.app.bossapi.dto.driver.SubmitDriverDTO;
import br.com.boss.app.bossapi.dto.driver.UniqueDriverDTO;
import br.com.boss.app.bossapi.model.Driver;
import br.com.boss.app.bossapi.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/driver")
@Tag(name = "Drivers", description = "Path relacionado a operações de motoristas")
public class DriverController {

    private final DriverService service;

    DriverController(DriverService service){
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Listar motoristas", description = "Retorna uma lista com todos os motoristas cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motoristas encontrados no banco de dados")
    })
    public ResponseEntity<List<DriverDTO>> getDrivers() {
        List<DriverDTO> drivers = this.service.getAll();

        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/unique/{uuid}")
    @Operation(summary = "Buscar motorista por UUID", description = "Retorna um motorista correspondente ao UUID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motorista encontrado"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado")
    })
    public ResponseEntity<UniqueDriverDTO> uniqueDriver(@PathVariable String uuid) throws Exception {
        UniqueDriverDTO driver = this.service.getUnique(uuid);

        if (driver == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(driver);
        }
    }

    @PostMapping("/submit")
    @Transactional
    @Operation(summary = "Cadastrar motorista", description = "Cadastra um motorista no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Motorista cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos")
    })
    public ResponseEntity<SubmitDriverDTO> submitDriver(@RequestBody @Valid Driver newDriverData, UriComponentsBuilder uriBuilder) throws Exception {
        SubmitDriverDTO driver = this.service.insert(newDriverData);

        URI uri = uriBuilder.path("/driver/unique/{uuid}").buildAndExpand(driver.getUuid()).toUri();

        return ResponseEntity.created(uri).body(driver);
    }

    @PutMapping("/alter/{uuid}")
    @Transactional
    @Operation(summary = "Alterar motorista", description = "Altera um motorista do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motorista alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado")
    })
    public ResponseEntity<SubmitDriverDTO> alterDriver(@PathVariable String uuid, @RequestBody @Valid Driver driverData) throws Exception {
        SubmitDriverDTO driver = this.service.update(driverData, uuid);

        if (driver == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(driver);
        }
    }

    @DeleteMapping("/{uuid}")
    @Transactional
    @Operation(summary = "Deletar motorista", description = "Deleta um motorista do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Motorista deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Motorista não encontrado")
    })
    public ResponseEntity<Void> deleteDriver(@PathVariable String uuid) throws Exception {
        if (this.service.delete(uuid)) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
