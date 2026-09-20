package com.rota.facil.journey.http.dto.response.routes;

import java.util.UUID;

public record CreateRouteInstitutionResponseDTO(
        UUID id,
        String name,
        String latitude,
        String longitude
) {
}
