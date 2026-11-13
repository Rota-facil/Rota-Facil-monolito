package com.rota.facil.security.http.exceptions;

public class EmailAlreadyExistsExceptions extends RuntimeException {
    public EmailAlreadyExistsExceptions() {
        super("Este email já está em uso");
    }
}
