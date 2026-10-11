package com.rota.facil.vehicles.http.dto.request.vehicles;

import com.rota.facil.vehicles.domain.VehicleType;

import java.util.UUID;

public record CreateVehicleRequest(
    UUID driverId,
    Long capacity,
    String plate,
    VehicleType vehicleType
) {
}
