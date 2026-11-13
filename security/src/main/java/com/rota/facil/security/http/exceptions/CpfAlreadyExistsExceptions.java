package com.rota.facil.security.http.exceptions;

public class CpfAlreadyExistsExceptions extends RuntimeException {
    public CpfAlreadyExistsExceptions() {
        super("Este cpf já está em uso");
    }
}
