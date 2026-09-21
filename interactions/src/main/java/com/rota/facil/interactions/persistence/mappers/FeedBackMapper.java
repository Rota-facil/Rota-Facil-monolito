package com.rota.facil.interactions.persistence.mappers;

import com.rota.facil.interactions.http.dto.response.feedbacks.FeedBackResponse;
import com.rota.facil.interactions.persistence.entities.FeedBackEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedBackMapper {
    FeedBackResponse map(FeedBackEntity entity);
}
