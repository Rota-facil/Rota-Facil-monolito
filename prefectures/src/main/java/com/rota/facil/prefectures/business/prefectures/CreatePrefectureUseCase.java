package com.rota.facil.prefectures.business.prefectures;

import com.rota.facil.prefectures.entitites.PrefectureEntity;
import com.rota.facil.prefectures.http.dto.request.prefecture.CreatePrefectureRequest;
import com.rota.facil.prefectures.http.dto.response.prefecture.CreatePrefectureResponse;
import com.rota.facil.prefectures.http.mappers.PrefectureMapper;
import com.rota.facil.prefectures.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePrefectureUseCase {
    private final ApplicationEventPublisher eventPublisher;
    private final PrefectureMapper prefectureMapper;
    private final PrefectureRepository prefectureRepository;

    @Transactional
    public CreatePrefectureResponse execute(CreatePrefectureRequest request) {
        PrefectureEntity prefecture = prefectureMapper.map(request);

        PrefectureEntity saved = prefectureRepository.save(prefecture);

        eventPublisher.publishEvent(request);

        return prefectureMapper.map(saved);
    }
}
