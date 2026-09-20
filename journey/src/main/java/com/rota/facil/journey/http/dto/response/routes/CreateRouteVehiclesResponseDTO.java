package com.rota.facil.journey.http.dto.response.routes;

import com.rota.facil.vehicles.domain.VehicleType;

import java.util.UUID;

public record CreateRouteVehiclesResponseDTO(
        UUID id,
        VehicleType vehicleType,
        String plate
) {
}
