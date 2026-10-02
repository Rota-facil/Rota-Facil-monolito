package com.rota.facil.journey.http.dto.request.routes;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record UpdateRouteInstitutionRequest(
        @NotNull(message = "Instituição é obrigatório")
        UUID institutionId,

        @NotNull(message = "horário de ida de instituição é obrigatório")
        LocalTime institutionGoing,

        @NotNull(message = "horário de volta de instituição é obrigatório")
        LocalTime institutionFinish
) {
}
