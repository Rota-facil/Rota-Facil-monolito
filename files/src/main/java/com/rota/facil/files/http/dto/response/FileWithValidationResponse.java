package com.rota.facil.files.http.dto.response;

import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.enums.FileValidationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileWithValidationResponse(
        UUID id,
        String originalFilename,
        String url,
        FileCategory fileCategory,
        FileValidationStatus validationStatus,
        LocalDateTime createdAt
) {
}
