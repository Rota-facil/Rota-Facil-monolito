package com.rota.facil.vehicles.http.dto.request.vehicles;

import com.rota.facil.vehicles.domain.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record UpdateVehicleRequest(
        UUID driverId,
        @Positive(message = "capacidade deve ser maior que zero") Long capacity,
        @NotBlank(message = "placa é obrigatória") String plate,
        VehicleStatus status
) {
}
