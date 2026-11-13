package com.rota.facil.journey.http.exceptions;

public class TripAlreadyCancelledException extends RuntimeException {
    public TripAlreadyCancelledException() {
        super("A viagem já foi cancelada anteriormente");
    }
}
