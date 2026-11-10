package com.rota.facil.users.http.dto.response.users;

import com.rota.facil.prefectures.domain.Region;

import java.util.UUID;

public record UserPrefectureResponse(
        UUID id,
        String name,
        Region region
) {
}
