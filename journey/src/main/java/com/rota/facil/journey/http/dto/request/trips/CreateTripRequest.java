package com.rota.facil.journey.http.dto.request.trips;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTripRequest(
        @NotBlank(message = "Nome da viagem é obrigatório")
        String name,

        @NotNull(message = "Rota é obrigatória")
        UUID routeId,

        @NotNull(message = "Veículo é obrigatório")
        UUID vehicleId
) {
}
