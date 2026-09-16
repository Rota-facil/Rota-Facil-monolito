package com.rota.facil.security.exceptions;

public class UserTokenNotFoundExceptions extends RuntimeException {
    public UserTokenNotFoundExceptions() {
        super("Token não encontrado");
    }
}
