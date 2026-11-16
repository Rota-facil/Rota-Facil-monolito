package com.rota.facil.files.business.useCases.boardPoints;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.repositories.BoardPointFilesRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListBoardPointPhotosUseCase {
    private final BoardPointFilesRepository boardPointFilesRepository;
    private final FileMapper fileMapper;

    @Transactional(readOnly = true)
    public List<FileResponse> execute(UUID boardPointId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();

        return this.boardPointFilesRepository.findAllByBoardPointIdAndPrefectureIdAndFileCategory(
                        boardPointId, prefectureId, FileCategory.BOARD_POINT_PHOTO
                ).stream()
                .map(fileMapper::map)
                .toList();
    }
}
