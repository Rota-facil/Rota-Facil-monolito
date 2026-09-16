package com.rota.facil.places.http.dto.response.boardpoints;

import java.util.UUID;

public record CreateBoardPointResponse(
        UUID id,
        UUID prefectureId,
        String name,
        Double latitude,
        Double longitude
) {
}
