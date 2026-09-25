package com.rota.facil.places.business.boardpoints;

import com.rota.facil.places.http.dto.request.boardpoints.CreateBoardPointRequest;
import com.rota.facil.places.http.dto.response.boardpoints.CreateBoardPointResponse;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.mappers.BoardPointMapper;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.rota.facil.annotations.UseCase;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@UseCase
@RequiredArgsConstructor
public class CreateBoardPointUseCase {
    private final BoardPointRepository boardPointRepository;
    private final BoardPointMapper boardPointMapper;


    public CreateBoardPointResponse execute(CreateBoardPointRequest request, UserEntity currentUser) {
        BoardPointEntity preSaved = this.boardPointMapper.map(request);
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(request.longitude(), request.latitude()));

        preSaved.setPrefectureId(currentUser.getPrefectureId());
        preSaved.setGeom(point);

        return this.boardPointMapper.map(this.boardPointRepository.save(preSaved));
    }
}
