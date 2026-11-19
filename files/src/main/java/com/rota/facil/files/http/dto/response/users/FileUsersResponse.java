package com.rota.facil.files.http.dto.response.users;

import com.rota.facil.files.domain.enums.FileCategory;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileUsersResponse(
        UUID id,
        UUID userId,
        String originalFilename,
        String url,
        FileCategory fileCategory,
        LocalDateTime createdAt
) {
}
