package com.rota.facil.users.business;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.mappers.UserMapper;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class FetchMeUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse execute(UserEntity currentUser) {
        return this.userMapper.map(currentUser);
    }
}
