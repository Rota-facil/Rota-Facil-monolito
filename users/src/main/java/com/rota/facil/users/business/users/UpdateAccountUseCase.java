package com.rota.facil.users.business.users;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.users.business.helpers.users.ValidateCpfHelper;
import com.rota.facil.users.business.helpers.users.ValidateEmailHelper;
import com.rota.facil.users.domain.UpdateUserData;
import com.rota.facil.users.http.dto.request.users.UpdateAccountRequest;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.mappers.UserMapper;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateAccountUseCase {
    private final ValidateEmailHelper validateEmailHelper;
    private final ValidateCpfHelper validateCpfHelper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse execute(UserEntity currentUser, UpdateAccountRequest request) {
        UpdateUserData updateUserData = this.userMapper.map(request);

        if(!currentUser.getEmail().equals(updateUserData.email())) this.validateEmailHelper.execute(updateUserData.email());
        if (!currentUser.getCpf().equals(updateUserData.cpf())) this.validateCpfHelper.execute(updateUserData.cpf());

        currentUser.update(updateUserData);

        return this.userMapper.map(
                this.userRepository.save(currentUser)
        );
    }
}
