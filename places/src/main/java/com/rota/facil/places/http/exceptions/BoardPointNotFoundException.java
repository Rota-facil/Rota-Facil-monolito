package com.rota.facil.places.http.exceptions;

public class BoardPointNotFoundException extends RuntimeException {
    public BoardPointNotFoundException(String message) {
        super(message);
    }
    public BoardPointNotFoundException() {
        super("Ponto de embarque não foi encontrado");
    }
}
