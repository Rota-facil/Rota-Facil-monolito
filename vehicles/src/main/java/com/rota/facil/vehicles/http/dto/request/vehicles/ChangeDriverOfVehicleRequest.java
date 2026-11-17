package com.rota.facil.vehicles.http.dto.request.vehicles;

import java.util.UUID;

public record ChangeDriverOfVehicleRequest(
        UUID driverId
) {
}
