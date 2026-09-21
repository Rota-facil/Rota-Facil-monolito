package com.rota.facil.vehicles.domain.exceptions;

public class VehicleInOperationException extends RuntimeException {
    public VehicleInOperationException() {
        this("Não é possível alterar o motorista enquanto o veículo está em operação");
    }

    public VehicleInOperationException(String message) {
        super(message);
    }
}
