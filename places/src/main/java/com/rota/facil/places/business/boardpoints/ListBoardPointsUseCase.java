package com.rota.facil.places.business.boardpoints;

import com.rota.facil.places.http.dto.response.boardpoints.BoardPointResponse;
import com.rota.facil.places.persistence.mappers.BoardPointMapper;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rota.facil.annotations.UseCase;

@UseCase
@RequiredArgsConstructor
public class ListBoardPointsUseCase {
    private final BoardPointRepository boardPointRepository;
    private final BoardPointMapper boardPointMapper;

    public Page<BoardPointResponse> execute(Pageable pageable, UserEntity currentUser) {
        return boardPointRepository
                .findAllByPrefectureIdAndActiveTrue(currentUser.getPrefectureId(), pageable)
                .map(boardPointMapper::mapToResponse);
    }
}
