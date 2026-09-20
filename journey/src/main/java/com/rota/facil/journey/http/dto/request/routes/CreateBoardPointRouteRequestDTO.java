package com.rota.facil.journey.http.dto.request.routes;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

public record CreateBoardPointRouteRequestDTO (
        @NotNull(message = "ponto de embarque é obrigatório")
        UUID boardPointId,

        @NotNull(message = "horário de ida de ponto de embarque é obrigatório")
        LocalTime boardTimeGoing,

        @NotNull(message = "horário de volta de ponto de embarque é obrigatório")
        LocalTime boardTimeFinish
) {
}
