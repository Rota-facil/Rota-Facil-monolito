package com.rota.facil.prefectures.http.dto.request.prefecture;

import com.rota.facil.prefectures.domain.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePrefectureRequest(
        @NotBlank(message = "nome da prefeitura é obrigatório")
        String name,

        @NotNull(message = "Região é obrigatório")
        Region region,

        @NotNull(message = "Usuario default da prefeitura eh obrigatório")
        PrefectureUserRequest prefectureUser
) {
}
