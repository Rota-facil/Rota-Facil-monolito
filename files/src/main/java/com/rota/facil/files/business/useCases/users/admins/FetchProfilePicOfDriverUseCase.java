package com.rota.facil.files.business.useCases.users.admins;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FetchProfilePicOfDriverUseCase {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileResponse execute(UserEntity currentUser, UUID driverId) {
        FileEntity profilePic = fileRepository.findByCreatorIdAndPrefectureIdAndFileCategory(
                driverId, currentUser.getPrefecture().getId(), FileCategory.PROFILE_PIC
        ).orElseThrow(() -> new FileNotFoundException("Foto de perfil não foi encontrada"));
        return fileMapper.map(profilePic);
    }
}
