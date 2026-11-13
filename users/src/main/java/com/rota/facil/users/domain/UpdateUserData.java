package com.rota.facil.users.domain;

public record UpdateUserData(
        String name,
        String email,
        String cpf
) {
}
