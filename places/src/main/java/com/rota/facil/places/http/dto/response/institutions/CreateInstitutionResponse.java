package com.rota.facil.places.http.dto.response.institutions;

import java.util.UUID;

public record CreateInstitutionResponse (
        UUID id,
        UUID prefectureId,
        String name,
        Double latitude,
        Double longitude
) {
}
