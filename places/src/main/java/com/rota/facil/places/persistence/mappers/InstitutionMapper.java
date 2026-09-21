package com.rota.facil.places.persistence.mappers;

import com.rota.facil.places.http.dto.request.institutions.CreateInstitutionRequest;
import com.rota.facil.places.http.dto.response.institutions.CreateInstitutionResponse;
import com.rota.facil.places.http.dto.response.institutions.InstitutionResponse;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    InstitutionEntity map(CreateInstitutionRequest request);
    CreateInstitutionResponse map(InstitutionEntity entity);
    InstitutionResponse mapToResponse(InstitutionEntity entity);
}
