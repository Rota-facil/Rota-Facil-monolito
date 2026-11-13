package com.rota.facil.users.http.dto.request.users;

public record UpdateAccountRequest(
        String name,
        String email,
        String cpf
) {
}
