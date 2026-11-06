package com.rota.facil.security.business.auth.helpers;

import com.rota.facil.security.exceptions.UserTokenNotFoundExceptions;
import com.rota.facil.security.persistence.entities.UserTokenEntity;
import com.rota.facil.security.persistence.repositories.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindUserTokenByUserIdHelper {
    private final UserTokenRepository userTokenRepository;

    public UserTokenEntity execute(UUID userId) {
        return this.userTokenRepository.findByUserId(userId)
                .orElseThrow(UserTokenNotFoundExceptions::new);
    }
}
