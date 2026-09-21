package com.rota.facil.places.http.dto.response.boardpoints;

import java.time.LocalDateTime;
import java.util.UUID;

public record BoardPointResponse(
        UUID id,
        UUID prefectureId,
        String name,
        Double latitude,
        Double longitude,
        LocalDateTime createdAt
) {
}
