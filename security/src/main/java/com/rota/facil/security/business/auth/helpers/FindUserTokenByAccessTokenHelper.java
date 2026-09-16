package com.rota.facil.security.business.auth.helpers;

import com.rota.facil.security.exceptions.UserTokenNotFoundExceptions;
import com.rota.facil.security.persistence.entities.UserTokenEntity;
import com.rota.facil.security.persistence.repositories.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindUserTokenByAccessTokenHelper {
    private final UserTokenRepository userTokenRepository;

    public UserTokenEntity execute(String accessToken) {
        return this.userTokenRepository.findByAccessToken(accessToken).orElseThrow(UserTokenNotFoundExceptions::new);
    }
}
