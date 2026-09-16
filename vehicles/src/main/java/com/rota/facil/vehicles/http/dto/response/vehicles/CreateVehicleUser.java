package com.rota.facil.vehicles.http.dto.response.vehicles;

import java.util.UUID;

public record CreateVehicleUser(
        UUID id,
        String name,
        String email
) {
}
