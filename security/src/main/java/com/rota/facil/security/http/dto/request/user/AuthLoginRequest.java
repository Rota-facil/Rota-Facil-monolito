package com.rota.facil.security.http.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "senha é obrigatória")
        String password
) {
}
