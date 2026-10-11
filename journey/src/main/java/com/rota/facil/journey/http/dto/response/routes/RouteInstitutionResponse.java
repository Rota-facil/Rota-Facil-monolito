package com.rota.facil.journey.http.dto.response.routes;

import java.time.LocalTime;
import java.util.UUID;

public record RouteInstitutionResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude,
        LocalTime institutionTimeGoing,
        LocalTime institutionTimeFinish
) {
}
