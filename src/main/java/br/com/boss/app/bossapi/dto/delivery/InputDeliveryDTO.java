package br.com.boss.app.bossapi.dto.delivery;

import br.com.boss.app.bossapi.dto.driver_truck.EntryDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InputDeliveryDTO(
        @NotNull(message = "Caminhão e Motorista não podem ser nulos")
        EntryDTO responsible,

        @NotNull(message = "Origem não pode ser nula")
        @NotBlank(message = "Origem não pode ser vazia")
        @Size(min = 3, max = 255)
        String origin,

        @NotNull(message = "Destino não pode ser nulo")
        @NotBlank(message = "Destino não pode ser vazio")
        @Size(min = 3, max = 255)
        String destination,

        @NotNull(message = "Valor por tonelada não pode ser nulo")
        Double valuePerTon,

        @NotNull(message = "Peso não pode ser nulo")
        Double weight,

        @NotNull(message = "Parte do motorista não pode ser nula")
        Double driverShare,

        String observation
) {
}
