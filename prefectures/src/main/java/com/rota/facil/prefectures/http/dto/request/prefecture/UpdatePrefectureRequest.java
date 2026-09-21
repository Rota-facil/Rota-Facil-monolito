package com.rota.facil.prefectures.http.dto.request.prefecture;

import com.rota.facil.prefectures.domain.Region;

public record UpdatePrefectureRequest(
        String name,
        Region region
) {
}
