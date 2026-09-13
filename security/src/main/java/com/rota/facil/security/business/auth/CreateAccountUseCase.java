package com.rota.facil.security.business.auth;

import com.rota.facil.prefectures.business.helpers.FetchPrefectureByIdHelper;
import com.rota.facil.prefectures.entitites.PrefectureEntity;
import com.rota.facil.security.business.auth.helpers.ValidationCreateAccountHelper;
import com.rota.facil.security.entities.UserTokenEntity;
import com.rota.facil.security.http.dto.request.user.CreateAccountRequest;
import com.rota.facil.security.http.dto.response.token.TokenResponse;
import com.rota.facil.security.service.JWTService;
import com.rota.facil.users.domain.Role;
import com.rota.facil.users.entities.UserEntity;
import com.rota.facil.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {
    private final FetchPrefectureByIdHelper fetchPrefectureByIdHelper;
    private final ValidationCreateAccountHelper validationCreateAccountHelper;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponse execute(CreateAccountRequest request) {
        PrefectureEntity prefecture = this.fetchPrefectureByIdHelper.execute(request.prefectureId());

        this.validationCreateAccountHelper.execute(request.cpf(), request.email());

        UserEntity newUser = UserEntity.builder()
                .cpf(request.cpf())
                .email(request.email())
                .name(request.name())
                .prefectureId(prefecture.getId())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.STUDENT)
                .build();

        UserEntity userCreated = this.userRepository.save(newUser);

        UserTokenEntity generatedToken = this.jwtService.generateTokenForNewUser(userCreated);

        return new TokenResponse(generatedToken.getAccessToken(), generatedToken.getRefreshToken());
    }
}
