package com.rota.facil.security.exceptions;

public class CpfAlreadyExistsExceptions extends RuntimeException {
    public CpfAlreadyExistsExceptions() {
        super("Este cpf já está em uso");
    }
}
