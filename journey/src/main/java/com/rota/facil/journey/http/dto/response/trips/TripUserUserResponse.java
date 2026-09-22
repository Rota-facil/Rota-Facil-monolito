package com.rota.facil.journey.http.dto.response.trips;

import java.util.UUID;

public record TripUserUserResponse(
        UUID id,
        String email
) {
}
