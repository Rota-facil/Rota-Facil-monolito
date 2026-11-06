package com.rota.facil.security.business;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.prefectures.exceptions.PrefectureNotFoundExceptions;
import com.rota.facil.prefectures.persistence.entitites.PrefectureEntity;
import com.rota.facil.prefectures.persistence.repositories.PrefectureRepository;
import com.rota.facil.security.exceptions.TimeForAccessCodeCompleteGooglehasExpiredException;
import com.rota.facil.security.exceptions.TokenCompleteGoogleLoginNotFoundException;
import com.rota.facil.security.http.dto.request.google.CompleteGoogleLoginRequest;
import com.rota.facil.security.http.dto.response.token.TokenResponse;
import com.rota.facil.security.persistence.entities.TokenCompleteGoogleLoginEntity;
import com.rota.facil.security.persistence.entities.UserTokenEntity;
import com.rota.facil.security.persistence.repositories.TokenCompleteGoogleLoginRepository;
import com.rota.facil.security.persistence.repositories.UserTokenRepository;
import com.rota.facil.security.service.JWTService;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class CompleteGoogleLoginUseCase {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final PrefectureRepository prefectureRepository;
    private final TokenCompleteGoogleLoginRepository tokenCompleteGoogleLoginRepository;

    @Transactional
    public TokenResponse execute(UUID pendingToken, CompleteGoogleLoginRequest request) {
        PrefectureEntity prefectureFound = this.prefectureRepository.findById(request.prefectureId())
                .orElseThrow(PrefectureNotFoundExceptions::new);

        TokenCompleteGoogleLoginEntity tokenCompleteGoogleLogin = this.tokenCompleteGoogleLoginRepository.findByPendingToken(pendingToken)
                .orElseThrow(TokenCompleteGoogleLoginNotFoundException::new);

        if (Duration.between(tokenCompleteGoogleLogin.getExpiration(), LocalDateTime.now()).toMinutes() > 15) {
            throw new TimeForAccessCodeCompleteGooglehasExpiredException();
        }

        UserEntity user = tokenCompleteGoogleLogin.getUser();
        user.setPrefectureId(prefectureFound.getId());
        user.setCpf(request.cpf());

       this.tokenCompleteGoogleLoginRepository.delete(tokenCompleteGoogleLogin);

       UserEntity savedUser = this.userRepository.save(user);

       UserTokenEntity userToken = this.userTokenRepository.save(
               UserTokenEntity.builder()
                       .user(user)
                       .accessToken(jwtService.generateAccessToken(savedUser))
                       .refreshToken(jwtService.generateRefreshToken(savedUser))
                       .build()
       );

       return new TokenResponse(userToken.getAccessToken(), userToken.getRefreshToken());
    }
}