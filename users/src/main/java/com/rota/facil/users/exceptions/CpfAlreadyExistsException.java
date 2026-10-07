package com.rota.facil.users.exceptions;

public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String message) {
        super(message);
    }

    public CpfAlreadyExistsException() {
        super("Já existe usuário cadastrado com esse cpf");
    }
}
