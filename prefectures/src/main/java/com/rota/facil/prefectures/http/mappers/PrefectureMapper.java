package com.rota.facil.prefectures.http.mappers;

import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.prefectures.http.dto.request.prefecture.CreatePrefectureRequest;
import com.rota.facil.prefectures.http.dto.response.prefecture.CreatePrefectureResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrefectureMapper {
    PrefectureEntity map(CreatePrefectureRequest request);
    CreatePrefectureResponse map(PrefectureEntity entity);
}
