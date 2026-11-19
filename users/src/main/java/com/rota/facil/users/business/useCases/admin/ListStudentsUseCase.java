package com.rota.facil.users.business.useCases.admin;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.mappers.UserMapper;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
@RequiredArgsConstructor
public class ListStudentsUseCase {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Page<UserResponse> execute(UserEntity currentUser, Pageable pageable) {
        return this.userRepository.findAllActiveStudentsByPrefectureId(currentUser.getPrefecture().getId(), pageable)
                .map(this.userMapper::map);
    }
}
