package com.rota.facil.journey.http.dto.request.routes;

import com.rota.facil.journey.domain.DaysOfWeek;
import com.rota.facil.journey.domain.Shift;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record CreateRouteRequest(
        @NotNull(message = "turno é obrigatório")
        Shift shift,

        @NotNull(message = "nome da rota é obrigatório")
        String name,

        @NotNull(message = "horário de ida é obrigatório")
        LocalTime going,

        @NotNull(message = "horário de volta é obrigatório")
        LocalTime return_,

        @NotNull(message = "horário de finalizaçao de ida é obrigatório")
        LocalTime goingFinish,

        @NotNull(message = "horário de finalização de volta é obrigatório")
        LocalTime returnFinish,

        @NotNull(message = "selecione pelo menos um dia da semana que ônibus fara a rota")
        List<DaysOfWeek> daysOfWeek,

        @NotNull(message = "selecione pelo menos uma instiuiçao é obrigátorio")
        Set<UUID> institutionsIds,

        @NotNull(message = "veículos são obrigatórios")
        List<UUID> vehicles,

        @NotNull(message = "pontos de embarque são obrigatórios")
        List<@Valid CreateBoardPointRouteRequestDTO> boardPoints
) {
}
