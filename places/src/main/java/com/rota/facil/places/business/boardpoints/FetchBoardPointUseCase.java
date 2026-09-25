package com.rota.facil.places.business.boardpoints;

import com.rota.facil.places.business.helpers.boardpoints.FindBoardPointByIdHelper;
import com.rota.facil.places.http.dto.response.boardpoints.BoardPointResponse;
import com.rota.facil.places.persistence.mappers.BoardPointMapper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import com.rota.facil.annotations.UseCase;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchBoardPointUseCase {
    private final FindBoardPointByIdHelper findBoardPointByIdHelper;
    private final BoardPointMapper boardPointMapper;

    public BoardPointResponse execute(UUID boardPointId, UserEntity currentUser) {
        return boardPointMapper.mapToResponse(
                findBoardPointByIdHelper.execute(boardPointId, currentUser.getPrefectureId())
        );
    }
}
