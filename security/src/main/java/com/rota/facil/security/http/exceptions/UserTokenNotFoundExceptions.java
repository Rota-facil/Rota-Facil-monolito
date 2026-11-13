package com.rota.facil.security.http.exceptions;

public class UserTokenNotFoundExceptions extends RuntimeException {
    public UserTokenNotFoundExceptions() {
        super("Token não encontrado");
    }
}
