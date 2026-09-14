package com.rota.facil.prefectures.spring.events.dto.send;

import jakarta.validation.constraints.NotBlank;

public record CreateDefaultAdminUserEventSend(
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
