package com.rota.facil.journey.persistence.mappers;

import com.rota.facil.journey.http.dto.response.trips.TripResponse;
import com.rota.facil.journey.persistence.entities.TripEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripMapper {
    TripResponse map(TripEntity entity);
}
