package com.rota.facil.vehicles.http.exceptions;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException() {
        super("Veículo não foi encontrado");
    }
}
