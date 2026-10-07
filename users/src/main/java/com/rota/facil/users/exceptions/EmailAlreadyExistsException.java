package com.rota.facil.users.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException() {
        super("Já existe usuário cadastrado com esse email");
    }
}
