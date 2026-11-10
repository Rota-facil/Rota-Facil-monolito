package com.rota.facil.files.business.boardPoints;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.FindBoardPointPhotoByIdHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileResponseMapper;
import com.rota.facil.places.business.helpers.boardpoints.FindBoardPointByIdHelper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchBoardPointPhotoUseCase {
    private final FindBoardPointByIdHelper findBoardPointByIdHelper;
    private final FindBoardPointPhotoByIdHelper findBoardPointPhotoByIdHelper;
    private final FileResponseMapper fileResponseMapper;

    @Transactional(readOnly = true)
    public FileResponse execute(UUID boardPointId, UUID photoId, UserEntity currentUser) {
        UUID prefectureId = currentUser.getPrefecture().getId();
        findBoardPointByIdHelper.execute(boardPointId, prefectureId);
        return fileResponseMapper.map(findBoardPointPhotoByIdHelper.execute(photoId, boardPointId, prefectureId));
    }
}
