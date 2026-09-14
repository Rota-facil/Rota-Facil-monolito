package com.rota.facil.security.spring.events.dto.receive;

import jakarta.validation.constraints.NotBlank;

public record CreateDefaultAdminUserEventReceive (
        @NotBlank(message = "nome é obrigatório")
        String name,

        @NotBlank(message = "email é obrigatório")
        String email,

        @NotBlank(message = "cpf é obrigatório")
        String cpf,

        @NotBlank(message = "senha é obrigatório")
        String password
) {
}
