package com.rota.facil.places.http.exceptions;

public class InstitutionNotFoundException extends RuntimeException {
    public InstitutionNotFoundException(String message) {
        super(message);
    }

    public InstitutionNotFoundException() {
        super("Instituição não foi encontrada");
    }
}
