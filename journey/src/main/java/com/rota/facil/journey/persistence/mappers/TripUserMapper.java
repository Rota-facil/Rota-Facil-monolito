package com.rota.facil.journey.persistence.mappers;

import com.rota.facil.journey.http.dto.response.trips.TripStudentsResponse;
import com.rota.facil.journey.http.dto.response.trips.TripUserResponse;
import com.rota.facil.journey.persistence.entities.TripUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TripUserMapper {
    TripUserResponse map(TripUserEntity entity);

    @Mapping(target = "institutionName", source = "institution.name")
    @Mapping(target = "boardPointName", source = "boardPoint.name")
    TripStudentsResponse mapTripStudents(TripUserEntity entity);
}
