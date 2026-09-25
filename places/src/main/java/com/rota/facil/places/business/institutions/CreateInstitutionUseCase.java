package com.rota.facil.places.business.institutions;

import com.rota.facil.places.http.dto.request.institutions.CreateInstitutionRequest;
import com.rota.facil.places.http.dto.response.institutions.CreateInstitutionResponse;
import com.rota.facil.places.persistence.entitites.InstitutionEntity;
import com.rota.facil.places.persistence.mappers.InstitutionMapper;
import com.rota.facil.places.persistence.repositories.InstitutionRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import com.rota.facil.annotations.UseCase;

@UseCase
@RequiredArgsConstructor
public class CreateInstitutionUseCase {
    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;

    public CreateInstitutionResponse execute(CreateInstitutionRequest request, UserEntity currentUser) {
        InstitutionEntity preSaved = this.institutionMapper.map(request);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));

        preSaved.setGeom(point);
        preSaved.setPrefectureId(currentUser.getPrefectureId());

        return this.institutionMapper.map(this.institutionRepository.save(preSaved));
    }
}
