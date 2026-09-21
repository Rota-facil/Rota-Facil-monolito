package com.rota.facil.vehicles.domain.exceptions;

public class VehicleInOperationException extends RuntimeException {
    public VehicleInOperationException() {
        super("Não é possível alterar o motorista enquanto o veículo está em operação");
    }
}
