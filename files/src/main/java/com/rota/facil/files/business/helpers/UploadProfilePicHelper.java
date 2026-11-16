package com.rota.facil.files.business.helpers;

import com.rota.facil.files.domain.enums.FileCategory;
import com.rota.facil.files.persistence.entities.FileEntity;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.files.storage.FileStorage;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class UploadProfilePicHelper {
    private final FileRepository fileRepository;
    private final FileStorage fileStorage;

    public FileEntity execute(UserEntity user, MultipartFile file) {
        FileEntity profilePicFound = this.fileRepository.findByCreatorIdAndPrefectureIdAndFileCategory(
                user.getId(), user.getPrefecture().getId(), FileCategory.PROFILE_PIC
        ).orElseGet(() -> FileEntity.newFile(
                user, user.getPrefecture(), file.getOriginalFilename(), file.getContentType(),
                file.getSize(), FileCategory.PROFILE_PIC
        ));

        if (profilePicFound.getId() != null) {
            profilePicFound.update(file.getOriginalFilename(), file.getContentType(), file.getSize());
            this.fileStorage.delete(profilePicFound.getObjectKey());
            profilePicFound.generateObjectKey();
        }

        this.fileStorage.upload(file, profilePicFound.getObjectKey());
        return this.fileRepository.save(profilePicFound);
    }
}
