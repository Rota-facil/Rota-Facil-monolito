package com.rota.facil.vehicles.domain;

import com.rota.facil.users.persistence.entities.UserEntity;

public record UpdateVehicleData(
        Long capacity,
        String plate,
        VehicleStatus status,
        UserEntity driver
) {
}
