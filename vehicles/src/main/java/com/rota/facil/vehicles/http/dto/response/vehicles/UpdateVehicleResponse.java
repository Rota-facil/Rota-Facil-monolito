package com.rota.facil.vehicles.http.dto.response.vehicles;

import com.rota.facil.vehicles.domain.VehicleStatus;
import com.rota.facil.vehicles.domain.VehicleType;
import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateVehicleResponse(
        UUID id,
        CreateVehicleUser driver,
        UUID prefectureId,
        Long capacity,
        String plate,
        boolean active,
        VehicleStatus status,
        VehicleType vehicleType,
        LocalDateTime createdAt
) {
}
