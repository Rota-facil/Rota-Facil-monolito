package com.rota.facil.vehicles.http.dto.response.vehicles;

import java.util.UUID;

public record VehicleUserResponse(
        UUID id,
        String name,
        String email
) {
}
