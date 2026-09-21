package com.rota.facil.vehicles.http.exceptions;

public class DriverIsOnRouteException extends RuntimeException {
    public DriverIsOnRouteException() {
        super("Não é possível vincular motorista pois no momento ele está em rota");
    }
}
