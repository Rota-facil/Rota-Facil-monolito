package com.rota.facil.journey.http.exceptions;

public class TripUserNotFoundExeption extends RuntimeException {
    public TripUserNotFoundExeption(String message) {
        super(message);
    }

    public TripUserNotFoundExeption() {
        super("Aluno ainda não entrou na viagem");
    }
}
