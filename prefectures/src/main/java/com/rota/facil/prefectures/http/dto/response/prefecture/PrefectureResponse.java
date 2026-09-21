package com.rota.facil.prefectures.http.dto.response.prefecture;

import com.rota.facil.prefectures.domain.Region;

import java.util.UUID;

public record PrefectureResponse(
        UUID id,
        String name,
        Region region
) {
}
