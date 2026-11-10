package com.rota.facil.files.business.boardPoints;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.places.business.helpers.boardpoints.FindBoardPointByIdHelper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class ListBoardPointPhotosUseCase {
    private final FindBoardPointByIdHelper findBoardPointByIdHelper;
    private final FileRepository fileRepository;
    private final FileResponseMapper fileResponseMapper;

    @Transactional(readOnly = true)
    public List<FileResponse> execute(UUID boardPointId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findBoardPointByIdHelper.execute(boardPointId, prefectureId);
        return fileRepository.findAllByOwnerIdAndPrefectureIdAndFileCategoryOrderByCreatedAtDesc(
                        boardPointId, prefectureId, FileCategory.PHOTO
                ).stream()
                .filter(file -> file.getOwnerType() == OwnerType.BOARD_POINT)
                .map(fileResponseMapper::map)
                .toList();
    }
}
