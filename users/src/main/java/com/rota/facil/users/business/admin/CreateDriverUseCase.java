package com.rota.facil.users.business.admin;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.prefectures.business.helpers.FetchPrefectureByIdHelper;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.users.business.helpers.users.ValidateCpfHelper;
import com.rota.facil.users.business.helpers.users.ValidateEmailHelper;
import com.rota.facil.users.domain.Role;
import com.rota.facil.users.http.dto.request.users.CreateDriverRequest;
import com.rota.facil.users.http.dto.response.users.UserResponse;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.mappers.UserMapper;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@UseCase
@RequiredArgsConstructor
public class CreateDriverUseCase {
    private final ValidateCpfHelper validateCpfHelper;
    private final ValidateEmailHelper validateEmailHelper;
    private final FetchPrefectureByIdHelper fetchPrefectureByIdHelper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponse execute(UserEntity currentUser, CreateDriverRequest createDriver) {
        this.validateEmailHelper.execute(createDriver.email());
        this.validateCpfHelper.execute(createDriver.cpf());

        PrefectureEntity prefectureFound = this.fetchPrefectureByIdHelper.execute(currentUser.getPrefecture().getId());

        UserEntity driverEntity = this.userMapper.map(createDriver);

        driverEntity.setPassword(passwordEncoder.encode(createDriver.password()));
        driverEntity.setRole(Role.DRIVER);
        driverEntity.setPrefecture(prefectureFound);

        return this.userMapper.map(this.userRepository.save(driverEntity));
    }
}
