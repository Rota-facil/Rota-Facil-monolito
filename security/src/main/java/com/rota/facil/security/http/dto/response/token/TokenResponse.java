package com.rota.facil.security.http.dto.response.token;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
