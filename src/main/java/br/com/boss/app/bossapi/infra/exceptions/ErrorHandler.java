package br.com.boss.app.bossapi.infra.exceptions;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Tag(name = "Erros", description = "Tratamento de erros")
public class ErrorHandler {

    @ExceptionHandler(NoSuchElementException.class)
    @Operation(summary = "Erro 404", description = "Recurso não encontrado")
    @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    public ResponseEntity<?> error404NotFoundHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @Operation(summary = "Erro 400", description = "Requisição inválida")
    @ApiResponse(responseCode = "400", description = "URL mal formatada")
    public ResponseEntity<?> error400BadRequestHandler(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("URL mal formatada: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Operation(summary = "Erro 400", description = "Dados inválidos")
    @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos")
    public ResponseEntity<?> errorInvalidData(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        List<DadosErroValidacao> dados = new ArrayList<>();
        for (FieldError fe : errors) {
            dados.add(new DadosErroValidacao(fe.getField(), fe.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(dados);
    }

    @ExceptionHandler(BadRequestException.class)
    @Operation(summary = "Erro 400", description = "Dados relacionados a requisição não encontrados")
    @ApiResponse(responseCode = "400", description = "Dados fornecidos são inválidos")
    public ResponseEntity<?> error400BadRequestHandler(BadRequestException ex) {
        DadosErroValidacao dados = new DadosErroValidacao("Erro", ex.getMessage());

        return ResponseEntity.badRequest().body(dados);
    }
    private record DadosErroValidacao(String campo, String mensagem) {
    }
}