package com.rota.facil.security.http.exceptions;

public class TokenCompleteGoogleLoginNotFoundException extends RuntimeException {
    public TokenCompleteGoogleLoginNotFoundException(String message) {
        super(message);
    }
    public TokenCompleteGoogleLoginNotFoundException() {
        super("Código para completar login com google não foi encontrado");
    }
}
