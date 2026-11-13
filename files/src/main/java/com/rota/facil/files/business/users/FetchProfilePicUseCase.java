package com.rota.facil.files.business.users;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FetchProfilePicUseCase {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileResponse execute(UserEntity currentUser) {
        FileEntity profilePicFound = this.fileRepository.findByCreatorIdAndOwnerIdAndPrefectureIdAndFileCategory(
                currentUser.getId(),
                currentUser.getId(),
                currentUser.getPrefecture().getId(),
                FileCategory.PROFILE_PIC
        ).orElseThrow(() -> new FileNotFoundException("Foto de perfil não foi encontrada"));

        return this.fileMapper.map(profilePicFound);
    }
}
