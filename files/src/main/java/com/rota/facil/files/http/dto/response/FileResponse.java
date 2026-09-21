package com.rota.facil.files.http.dto.response;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import java.time.LocalDateTime;
import java.util.UUID;
public record FileResponse(UUID id, String originalFilename, String url, FileCategory fileCategory,
                           OwnerType ownerType, LocalDateTime createdAt) {}
