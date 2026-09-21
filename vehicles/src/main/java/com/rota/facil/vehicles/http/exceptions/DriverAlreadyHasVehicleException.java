package com.rota.facil.vehicles.http.exceptions;

public class DriverAlreadyHasVehicleException extends RuntimeException {
    public DriverAlreadyHasVehicleException() {
        super("Motorista já está vinculado a outro veículo");
    }
}
