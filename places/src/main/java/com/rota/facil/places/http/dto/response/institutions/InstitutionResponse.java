package com.rota.facil.places.http.dto.response.institutions;

import java.time.LocalDateTime;
import java.util.UUID;

public record InstitutionResponse(
        UUID id,
        UUID prefectureId,
        String name,
        Double latitude,
        Double longitude,
        LocalDateTime createdAt
) {
}
