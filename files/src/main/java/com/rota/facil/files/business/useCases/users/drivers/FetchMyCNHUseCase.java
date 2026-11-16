package com.rota.facil.files.business.useCases.users.drivers;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.http.dto.response.FileWithValidationResponse;
import com.rota.facil.files.http.exceptions.FileNotFoundException;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.entities.FileWithValidationEntity;
import com.rota.facil.files.persistence.repositories.FileWithValidationRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FetchMyCNHUseCase {
    private final FileWithValidationRepository fileWithValidationRepository;
    private final FileMapper fileMapper;

    public FileWithValidationResponse execute(UserEntity currentUser) {
        FileWithValidationEntity cnhFound = this.fileWithValidationRepository.findCNHByUserIdAndPrefectureId(currentUser.getId(), currentUser.getPrefecture().getId())
                .orElseThrow(() -> new FileNotFoundException("Você ainda não cadastrou sua CNH"));
        return this.fileMapper.map(cnhFound);
    }
}
