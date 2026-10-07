package com.rota.facil.users.http.dto.request.users;

import jakarta.validation.constraints.NotBlank;

public record CreateDriverRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Cpf é obrigatório")
        String cpf,

        @NotBlank(message = "Senha é obrigatório")
        String password
) {
}
