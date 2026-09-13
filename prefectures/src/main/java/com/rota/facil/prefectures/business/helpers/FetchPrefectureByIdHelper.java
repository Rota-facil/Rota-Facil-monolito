package com.rota.facil.prefectures.business.helpers;

import com.rota.facil.prefectures.entitites.PrefectureEntity;
import com.rota.facil.prefectures.exceptions.PrefectureNotFoundExceptions;
import com.rota.facil.prefectures.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FetchPrefectureByIdHelper {
    private final PrefectureRepository prefectureRepository;

    public PrefectureEntity execute(UUID prefectureId) {
        return prefectureRepository.findById(prefectureId)
                .orElseThrow(PrefectureNotFoundExceptions::new);
    }
}
