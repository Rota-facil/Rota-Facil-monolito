package com.rota.facil.journey.persistence.mappers;

import com.rota.facil.journey.http.dto.response.trips.TripUserResponse;
import com.rota.facil.journey.persistence.entities.TripUserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripUserMapper {
    TripUserResponse map(TripUserEntity entity);
}
