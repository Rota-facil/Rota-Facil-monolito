package com.rota.facil.files.business.useCases.users.admins;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.files.http.dto.request.users.ListProfilePicUsersRequest;
import com.rota.facil.files.http.dto.response.FileResponse;
import com.rota.facil.files.http.dto.response.users.FileUsersResponse;
import com.rota.facil.files.http.mappers.FileMapper;
import com.rota.facil.files.persistence.repositories.FileRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListProfilePIcUsersUseCase {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public List<FileUsersResponse> execute(UserEntity currentUser, ListProfilePicUsersRequest request) {
        return this.fileRepository.findAllByPrefectureIdAndUsersId(currentUser.getPrefecture().getId(), request.usersId())
                .stream()
                .map(this.fileMapper::mapWithUsers)
                .toList();

    }
}
