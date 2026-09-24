package com.rota.facil.journey.http.dto.request.trips;

import jakarta.validation.constraints.NotBlank;

public record CancelTripRequest(
        @NotBlank(message = "razão de cancelamento é obrigatória")
        String reasonOfCancellation
) {
}
