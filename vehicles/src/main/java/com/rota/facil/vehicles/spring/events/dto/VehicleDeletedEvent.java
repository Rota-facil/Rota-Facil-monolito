package com.rota.facil.vehicles.spring.events.dto;

import java.util.UUID;

public record VehicleDeletedEvent(UUID vehicleId) {
}
