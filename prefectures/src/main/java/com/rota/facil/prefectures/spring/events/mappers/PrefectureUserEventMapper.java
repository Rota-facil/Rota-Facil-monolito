package com.rota.facil.prefectures.spring.events.mappers;

import com.rota.facil.prefectures.http.dto.request.prefecture.PrefectureUserRequest;
import com.rota.facil.prefectures.spring.events.dto.send.CreateDefaultAdminUserEventSend;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrefectureUserEventMapper {
    CreateDefaultAdminUserEventSend map(PrefectureUserRequest request);
}
