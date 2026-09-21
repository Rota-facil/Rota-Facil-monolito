package com.rota.facil.interactions.http.dto.response.feedbacks;

import java.time.LocalDateTime;

public record FeedBackResponse(
        String senderEmail,
        String receiverEmail,
        Double note,
        String feedback,
        LocalDateTime createdAt
) {
}
