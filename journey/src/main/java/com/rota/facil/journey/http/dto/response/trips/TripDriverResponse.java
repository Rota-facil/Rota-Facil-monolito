package com.rota.facil.journey.http.dto.response.trips;

import java.util.UUID;

public record TripDriverResponse(
        UUID id,
        String email
) {
}
