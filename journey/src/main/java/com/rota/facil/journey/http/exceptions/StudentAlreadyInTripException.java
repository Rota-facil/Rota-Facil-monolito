package com.rota.facil.journey.http.exceptions;

public class StudentAlreadyInTripException extends RuntimeException {
    public StudentAlreadyInTripException() {
        super("Você já está inscrito nesta viagem");
    }
}
