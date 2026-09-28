package com.rota.facil.journey.http.dto.response.trips;

import java.util.UUID;

public record StudentResponse(
        UUID id,
        String name,
        String email
) {
}
