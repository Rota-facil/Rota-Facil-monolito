package com.rota.facil.security.business.auth;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.security.persistence.repositories.UserTokenRepository;
import com.rota.facil.users.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class LogoutUseCase {
    private final UserTokenRepository userTokenRepository;

    @Transactional
    public void execute(UserEntity currentUser) {
        this.userTokenRepository.deleteByUserId(currentUser.getId());
    }
}
