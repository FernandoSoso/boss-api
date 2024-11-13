package br.com.boss.app.bossapi.dto.driver_truck;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EntryDTO(
        @NotNull(message = "Uuid do motorista não pode ser nulo")
        @NotBlank(message = "Uuid do motorista não pode ser vazio")
        String driverUuid,

        @NotNull(message = "Uuid do caminhão não pode ser nulo")
        @NotBlank(message = "Uuid do caminhão não pode ser vazio")
        String truckUuid) {
}
