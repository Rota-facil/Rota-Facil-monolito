package com.rota.facil.journey.http.dto.response.trips;

import java.util.UUID;

public record TripRouteBoardPointResponse(
        UUID id,
        String name,
        Double latitude,
        Double longitude
) {
}
