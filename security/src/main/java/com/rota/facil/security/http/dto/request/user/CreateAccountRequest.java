package com.rota.facil.security.http.dto.request.user;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateAccountRequest(
        @NotBlank(message = "Id da Prefeitura é obrigatório")
        UUID prefectureId,

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
