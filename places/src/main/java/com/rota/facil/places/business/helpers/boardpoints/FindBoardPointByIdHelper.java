package com.rota.facil.places.business.helpers.boardpoints;

import com.rota.facil.places.http.exceptions.BoardPointNotFoundException;
import com.rota.facil.places.persistence.entitites.BoardPointEntity;
import com.rota.facil.places.persistence.repositories.BoardPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindBoardPointByIdHelper {
    private final BoardPointRepository boardPointRepository;

    public BoardPointEntity execute(UUID boardPointId, UUID prefectureId) {
        return boardPointRepository.findByIdAndPrefectureIdAndActiveTrue(boardPointId, prefectureId)
                .orElseThrow(() -> new BoardPointNotFoundException("Ponto de embarque não foi encontrado"));
    }
}
