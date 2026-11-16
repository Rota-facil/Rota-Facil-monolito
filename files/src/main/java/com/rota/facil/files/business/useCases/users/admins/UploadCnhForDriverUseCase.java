package com.rota.facil.files.business.useCases.users.admins;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.business.helpers.UploadCnhHelper;
import com.rota.facil.files.http.dto.response.FileWithValidationResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import com.rota.facil.users.http.exceptions.UserNotFoundExceptions;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class UploadCnhForDriverUseCase {
    private final UserRepository userRepository;
    private final UploadCnhHelper uploadCnhHelper;
    private final FileMapper fileMapper;

    public FileWithValidationResponse execute(UserEntity currentUser, UUID driverId, MultipartFile file) {
        UserEntity driverFound = this.userRepository.findByIdAndPrefectureIdActive(driverId, currentUser.getPrefecture().getId())
                .orElseThrow(UserNotFoundExceptions::new);

        return this.fileMapper.map(
                this.uploadCnhHelper.execute(driverFound, file)
        );
    }
}
