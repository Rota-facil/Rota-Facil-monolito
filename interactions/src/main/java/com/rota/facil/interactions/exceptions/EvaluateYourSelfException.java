package com.rota.facil.interactions.exceptions;

public class EvaluateYourSelfException extends RuntimeException {
    public EvaluateYourSelfException(String message) {
        super(message);
    }

    public EvaluateYourSelfException() {
        super("Não é possível se auto avaliar");
    }
}
