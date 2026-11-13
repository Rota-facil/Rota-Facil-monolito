package com.rota.facil.journey.http.exceptions;

public class InvalidTripGoingReturnException extends RuntimeException {
    public InvalidTripGoingReturnException(String message) {
        super(message);
    }

    public InvalidTripGoingReturnException() {
        super("Selecione pelo menos a ida ou a volta");
    }
}
