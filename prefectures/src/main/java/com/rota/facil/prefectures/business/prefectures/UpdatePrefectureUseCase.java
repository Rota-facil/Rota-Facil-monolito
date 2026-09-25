package com.rota.facil.prefectures.business.prefectures;

import com.rota.facil.prefectures.business.helpers.FetchPrefectureByIdHelper;
import com.rota.facil.prefectures.domain.UpdatePrefectureData;
import com.rota.facil.prefectures.http.dto.request.prefecture.UpdatePrefectureRequest;
import com.rota.facil.prefectures.http.dto.response.prefecture.PrefectureResponse;
import com.rota.facil.prefectures.http.mappers.PrefectureMapper;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.prefectures.persistence.repositories.PrefectureRepository;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdatePrefectureUseCase {
    private final PrefectureRepository prefectureRepository;
    private final PrefectureMapper prefectureMapper;
    private final FetchPrefectureByIdHelper fetchPrefectureByIdHelper;

    @Transactional
    public PrefectureResponse execute(UUID prefectureId, UpdatePrefectureRequest request) {
        UpdatePrefectureData updateData = prefectureMapper.map(request);
        PrefectureEntity prefecture = fetchPrefectureByIdHelper.execute(prefectureId);

        prefecture.update(updateData);

        return prefectureMapper.mapToResponse(prefectureRepository.save(prefecture));
    }
}
