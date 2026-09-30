package com.rota.facil.journey.http.dto.response.routes;

import java.util.UUID;

public record RouteInstitutionResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude
) {
}
