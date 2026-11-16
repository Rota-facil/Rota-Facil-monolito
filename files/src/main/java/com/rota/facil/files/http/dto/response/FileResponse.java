package com.rota.facil.files.http.dto.response;
import com.rota.facil.files.domain.enums.FileCategory;

import java.time.LocalDateTime;
import java.util.UUID;
public record FileResponse(
        UUID id,
        String originalFilename,
        String url,
        FileCategory fileCategory,
        LocalDateTime createdAt
) {}
