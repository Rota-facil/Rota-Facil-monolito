package com.rota.facil.files.business.users;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.domain.FileCategory;
import com.rota.facil.files.domain.OwnerType;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
public class UploadProfilePicUseCase {
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;
    private final FileMapper fileMapper;

    public FileResponse execute(UserEntity currentUser, MultipartFile file) {
        FileEntity profilePicFound = this.fileRepository.findByIdAndOwnerIdAndPrefectureIdAndFileCategory(
                currentUser.getId(),
                currentUser.getId(),
                currentUser.getPrefecture().getId(),
                FileCategory.PROFILE_PIC
        ).orElseGet(
                () -> FileEntity.newFile(
                        currentUser.getId(),
                        currentUser.getId(),
                        currentUser.getPrefecture().getId(),
                        file.getOriginalFilename(),
                        file.getContentType(),
                        file.getSize(),
                        OwnerType.USER,
                        FileCategory.PROFILE_PIC
                )
        );

        if (profilePicFound.getId() != null) {
            profilePicFound.setOriginalFilename(file.getOriginalFilename());
            profilePicFound.setMimeType(file.getContentType());
            profilePicFound.setFileSizeBytes(file.getSize());

            this.fileStorage.delete(profilePicFound.getObjectKey());

            profilePicFound.setObjectKey(FileEntity.generateObjectKey(
                    profilePicFound.getOriginalFilename(),
                    profilePicFound.getOwnerId(),
                    profilePicFound.getOwnerType(),
                    profilePicFound.getFileCategory()
            ));
        }

        this.fileStorage.upload(file, profilePicFound.getObjectKey());

        FileEntity saved = this.fileRepository.save(profilePicFound);

        return this.fileMapper.map(saved);
    }
}
