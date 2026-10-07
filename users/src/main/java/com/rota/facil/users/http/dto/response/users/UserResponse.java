package com.rota.facil.users.http.dto.response.users;

import java.util.UUID;

public record UserResponse(
        UUID id,
        UUID prefectureId,
        String name,
        String email,
        String cpf
) {
}
