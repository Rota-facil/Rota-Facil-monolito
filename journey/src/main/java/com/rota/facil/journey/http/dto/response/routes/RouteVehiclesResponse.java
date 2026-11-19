package com.rota.facil.journey.http.dto.response.routes;

import java.util.UUID;

public record RouteVehiclesResponse(
        UUID id,
        String plate
) {
}
