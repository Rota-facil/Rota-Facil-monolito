package com.rota.facil.security.exceptions;

public class TimeForAccessCodeCompleteGooglehasExpiredException extends RuntimeException {
    public TimeForAccessCodeCompleteGooglehasExpiredException(String message) {
        super(message);
    }

    public TimeForAccessCodeCompleteGooglehasExpiredException() {
        super("Código de acesso para completar login com google expirou");
    }
}
