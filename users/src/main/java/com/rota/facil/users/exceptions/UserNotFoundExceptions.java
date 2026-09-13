package com.rota.facil.users.exceptions;

public class UserNotFoundExceptions extends RuntimeException {
    public UserNotFoundExceptions(String message) {
        super(message);
    }

    public UserNotFoundExceptions() {
        super("Usuário não foi encontrado");
    }
}
