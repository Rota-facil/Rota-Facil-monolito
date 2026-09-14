package com.rota.facil.prefectures.http.dto.request.prefecture;

import jakarta.validation.constraints.NotBlank;

public record PrefectureUserRequest(
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
