package com.rota.facil.journey.http.dto.response.trips;

import com.rota.facil.journey.domain.Delay;
import com.rota.facil.journey.domain.Progress;

import java.time.LocalDateTime;
import java.util.UUID;

public record TripStatusResponseDTO(
        UUID id,
        Progress progress,
        Delay delay,
        String description,
        LocalDateTime createdAt
) {
}
