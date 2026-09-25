package com.rota.facil.interactions.business.feedbacks;

import com.rota.facil.interactions.http.dto.response.feedbacks.FeedBackResponse;
import com.rota.facil.interactions.persistence.entities.FeedBackRepository;
import com.rota.facil.interactions.persistence.mappers.FeedBackMapper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListFeedBacksByUserUseCase {
    private final FeedBackRepository feedBackRepository;
    private final FeedBackMapper feedBackMapper;

    public List<FeedBackResponse> execute(UserEntity currentUser, UUID userId) {
        return this.feedBackRepository.findAllByReceiverIdAndPrefectureId(
                userId,
                currentUser.getPrefectureId()
        )
                .stream()
                .map(feedBackMapper::map)
                .toList();
    }
}
