package com.rota.facil.places.http.dto.request.boardpoints;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateBoardPointRequest(
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotNull(message = "A latitude é obrigatória")
        Double latitude,

        @NotNull(message = "A longitude é obrigatória")
        Double longitude
) {
}
