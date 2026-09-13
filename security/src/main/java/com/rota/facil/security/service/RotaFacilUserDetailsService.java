package com.rota.facil.security.service;

import com.rota.facil.security.entities.UserTokenEntity;
import com.rota.facil.security.repositories.UserTokenRepository;
import com.rota.facil.security.user.details.AuthenticatedUser;
import com.rota.facil.security.exceptions.UserNotFoundExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RotaFacilUserDetailsService implements UserDetailsService {
    private final UserTokenRepository userTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String accessToken) throws UsernameNotFoundException {
        return new AuthenticatedUser(
                userTokenRepository.findByAccessToken(accessToken)
                        .map(UserTokenEntity::getUser)
                        .orElseThrow(UserNotFoundExceptions::new)
        );
    }
}
