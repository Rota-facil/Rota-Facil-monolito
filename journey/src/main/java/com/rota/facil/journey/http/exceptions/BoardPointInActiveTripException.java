package com.rota.facil.journey.http.exceptions;

public class BoardPointInActiveTripException extends RuntimeException {
    public BoardPointInActiveTripException() {
        super("Não é possível excluir o ponto de embarque porque ele pertence a uma viagem em andamento");
    }
}
