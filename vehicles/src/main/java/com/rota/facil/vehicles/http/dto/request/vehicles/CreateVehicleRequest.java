package com.rota.facil.vehicles.http.dto.request.vehicles;

import com.rota.facil.vehicles.domain.VehicleType;

public record CreateVehicleRequest(
Long capacity,
String plate,
VehicleType vehicleType
) {
}
