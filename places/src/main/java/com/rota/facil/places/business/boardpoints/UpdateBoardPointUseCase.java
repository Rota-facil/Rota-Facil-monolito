package com.rota.facil.places.business.boardpoints;

import com.rota.facil.places.business.helpers.boardpoints.FindBoardPointByIdHelper;
import com.rota.facil.places.domain.UpdateBoardPointData;
import com.rota.facil.places.http.dto.request.boardpoints.UpdateBoardPointRequest;
import com.rota.facil.places.http.dto.response.boardpoints.BoardPointResponse;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.mappers.BoardPointMapper;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UpdateBoardPointUseCase {
    private final FindBoardPointByIdHelper findBoardPointByIdHelper;
    private final BoardPointRepository boardPointRepository;
    private final BoardPointMapper boardPointMapper;

    @Transactional
    public BoardPointResponse execute(UUID boardPointId, UpdateBoardPointRequest request, UserEntity currentUser) {
        BoardPointEntity boardPoint = findBoardPointByIdHelper.execute(
                boardPointId,
                currentUser.getPrefectureId()
        );
        UpdateBoardPointData updateData = boardPointMapper.map(request);

        boardPoint.update(updateData);

        return boardPointMapper.mapToResponse(boardPointRepository.save(boardPoint));
    }
}
