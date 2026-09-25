package com.rota.facil.prefectures.business.prefectures;

import com.rota.facil.prefectures.business.helpers.FetchPrefectureByIdHelper;
import com.rota.facil.prefectures.http.dto.response.prefecture.PrefectureResponse;
import com.rota.facil.prefectures.http.mappers.PrefectureMapper;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchPrefectureUseCase {
    private final FetchPrefectureByIdHelper fetchPrefectureByIdHelper;
    private final PrefectureMapper prefectureMapper;

    public PrefectureResponse execute(UUID prefectureId) {
        return prefectureMapper.mapToResponse(fetchPrefectureByIdHelper.execute(prefectureId));
    }
}
