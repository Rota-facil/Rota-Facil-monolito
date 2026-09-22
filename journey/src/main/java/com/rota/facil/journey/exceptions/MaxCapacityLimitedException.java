package com.rota.facil.journey.exceptions;

public class MaxCapacityLimitedException extends RuntimeException {
    public MaxCapacityLimitedException(String message) {
        super(message);
    }

    public MaxCapacityLimitedException() {
        super("O veículo já está cheio");
    }
}
