package com.rota.facil.prefectures.business.prefectures;

import com.rota.facil.prefectures.business.helpers.FetchPrefectureByIdHelper;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.prefectures.persistence.repositories.PrefectureRepository;
import com.rota.facil.prefectures.spring.events.dto.PrefectureDeactivatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeletePrefectureUseCase {
    private final FetchPrefectureByIdHelper fetchPrefectureByIdHelper;
    private final PrefectureRepository prefectureRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void execute(UUID prefectureId) {
        PrefectureEntity prefecture = fetchPrefectureByIdHelper.execute(prefectureId);

        prefecture.deactivate();
        prefectureRepository.save(prefecture);
        eventPublisher.publishEvent(new PrefectureDeactivatedEvent(prefectureId));
    }
}
