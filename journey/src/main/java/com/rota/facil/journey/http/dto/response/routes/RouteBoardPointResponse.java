package com.rota.facil.journey.http.dto.response.routes;

import java.util.UUID;

public record RouteBoardPointResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude
) {
}
