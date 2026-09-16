package com.rota.facil.places.business.institutions;

import com.rota.facil.places.http.dto.request.institutions.CreateInstitutionRequest;
import com.rota.facil.places.http.dto.response.institutions.CreateInstitutionResponse;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.mappers.InstitutionMapper;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.users.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateInstitutionUseCase {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public CreateInstitutionResponse execute(CreateInstitutionRequest request, UserEntity currentUser) {
        InstitutionEntity preSaved = this.institutionMapper.map(request);
        preSaved.setPrefectureId(currentUser.getPrefectureId());

        return this.institutionMapper.map(this.institutionRepository.save(preSaved));
    }
}
