package br.com.boss.app.bossapi.controller;

import br.com.boss.app.bossapi.dto.user.UniqueUserDTO;
import br.com.boss.app.bossapi.dto.user.UserDTO;
import br.com.boss.app.bossapi.model.User;
import br.com.boss.app.bossapi.service.UserService;
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
@RequestMapping("/user")
@Tag(name = "Users", description = "Path para manipulação de usuários")
public class UserController {

    private final UserService service;

    UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Listar usuários", description = "Retorna uma lista com todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
    })
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userList = service.getAll();

        return ResponseEntity.ok(userList);
    }

    @GetMapping("/unique/{uuid}")
    @Operation(summary = "Buscar usuário por UUID", description = "Retorna um usuário correspondente ao UUID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UniqueUserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<UniqueUserDTO> uniqueUser(@PathVariable String uuid) {
        UniqueUserDTO u = this.service.getUnique(uuid);

        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(u);
        }
    }

    @Transactional
    @PostMapping("/submit")
    @Operation(summary = "Cadastrar um novo usuário", description = "Cria um novo usuário e o adiciona à lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UniqueUserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
    })
    public ResponseEntity<UniqueUserDTO> submitUser(@RequestBody @Valid User newUserData, UriComponentsBuilder uriBuilder) throws Exception {
        UniqueUserDTO returnedUser = this.service.insert(newUserData);

        URI uri = uriBuilder.path("/user/{uuid}").buildAndExpand(newUserData.getUuid()).toUri();

        return ResponseEntity.created(uri).body(returnedUser);
    }

    @Transactional
    @PutMapping("/alter/{uuid}")
    @Operation(summary = "Alterar um usuário", description = "Altera um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário alterado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UniqueUserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<UniqueUserDTO> alterUser(@PathVariable String uuid, @RequestBody User userData) throws Exception {
        UniqueUserDTO returnedUser = this.service.update(userData, uuid);

        if (returnedUser == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(returnedUser);
        }
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar um usuário", description = "Deleta um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String uuid) {
        if (this.service.delete(uuid)) {
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}