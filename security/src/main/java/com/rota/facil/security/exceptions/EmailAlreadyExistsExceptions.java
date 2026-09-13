package com.rota.facil.security.exceptions;

public class EmailAlreadyExistsExceptions extends RuntimeException {
    public EmailAlreadyExistsExceptions() {
        super("Este email já está em uso");
    }
}
