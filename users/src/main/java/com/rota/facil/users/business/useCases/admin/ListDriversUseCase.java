package com.rota.facil.users.business.useCases.admin;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.mappers.UserMapper;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListDriversUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> execute(UserEntity currentUser) {
        return this.userRepository.findAllActiveDriversByPrefectureId(currentUser.getPrefecture().getId())
                .stream()
                .map(this.userMapper::map)
                .toList();
    }
}
