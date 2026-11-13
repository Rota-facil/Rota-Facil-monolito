package com.rota.facil.users.http.exceptions;

public class UserNotFoundExceptions extends RuntimeException {
    public UserNotFoundExceptions(String message) {
        super(message);
    }

    public UserNotFoundExceptions() {
        super("Usuário não foi encontrado");
    }
}
