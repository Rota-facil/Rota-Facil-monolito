package com.rota.facil.places.business.boardpoints;

import com.rota.facil.places.business.helpers.boardpoints.FindBoardPointByIdHelper;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import com.rota.facil.places.spring.events.dto.ValidateBoardPointDeactivationEvent;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBoardPointUseCase {
    private final FindBoardPointByIdHelper findBoardPointByIdHelper;
    private final BoardPointRepository boardPointRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void execute(UUID boardPointId, UserEntity currentUser) {
        BoardPointEntity boardPoint = findBoardPointByIdHelper.execute(
                boardPointId,
                currentUser.getPrefectureId()
        );

        eventPublisher.publishEvent(new ValidateBoardPointDeactivationEvent(boardPointId));

        boardPoint.deactivate();
        boardPointRepository.save(boardPoint);
    }
}
