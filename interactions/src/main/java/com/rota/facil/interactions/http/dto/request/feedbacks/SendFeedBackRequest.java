package com.rota.facil.interactions.http.dto.request.feedbacks;

import java.util.UUID;

public record SendFeedBackRequest(
        String feedback,
        Double note,
        UUID receiverId
) {
}
