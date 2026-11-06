package com.rota.facil.security.http.dto.request.google;

import java.util.UUID;

public record CompleteGoogleLoginRequest(
        UUID prefectureId,
        String cpf
) {
}
