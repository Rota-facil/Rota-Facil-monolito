package com.rota.facil.journey.http.dto.response.routes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record CreateRouteBoardPointResponseDTO(
        UUID id,
        String name,
        String latitude,
        String longitude,
        LocalTime boardTimeGoing,
        LocalTime boardTimeFinish
) {
}
