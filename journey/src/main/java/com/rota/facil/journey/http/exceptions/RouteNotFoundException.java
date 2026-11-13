package com.rota.facil.journey.http.exceptions;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(String message) {
        super(message);
    }

    public RouteNotFoundException() {
        super("Rota não foi encontrada");
    }
}
