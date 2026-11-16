package com.rota.facil.files.business.useCases.users;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.UploadProfilePicHelper;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
public class UploadProfilePicUseCase {
    private final UploadProfilePicHelper uploadProfilePicHelper;
    private final FileMapper fileMapper;

    public FileResponse execute(UserEntity currentUser, MultipartFile file) {
        return this.fileMapper.map(this.uploadProfilePicHelper.execute(currentUser, file));
    }
}
