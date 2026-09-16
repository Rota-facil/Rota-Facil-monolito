package com.rota.facil.places.business.boardpoints;

import com.rota.facil.places.http.dto.request.boardpoints.CreateBoardPointRequest;
import com.rota.facil.places.http.dto.response.boardpoints.CreateBoardPointResponse;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.mappers.BoardPointMapper;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateBoardPointUseCase {
    private final BoardPointRepository boardPointRepository;
    private final BoardPointMapper boardPointMapper;


    public CreateBoardPointResponse execute(CreateBoardPointRequest request, UserEntity currentUser) {
        BoardPointEntity preSaved = this.boardPointMapper.map(request);
        preSaved.setPrefectureId(currentUser.getPrefectureId());

        return this.boardPointMapper.map(this.boardPointRepository.save(preSaved));
    }
}
