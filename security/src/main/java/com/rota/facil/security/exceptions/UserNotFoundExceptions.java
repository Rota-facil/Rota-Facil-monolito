package com.rota.facil.security.exceptions;

public class UserNotFoundExceptions extends RuntimeException {
    public UserNotFoundExceptions() {
        super("Usuário não foi encontrado");
    }
}
