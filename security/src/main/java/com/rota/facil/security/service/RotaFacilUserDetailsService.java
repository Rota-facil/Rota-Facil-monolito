package com.rota.facil.security.service;

import com.rota.facil.security.user.details.AuthenticatedUser;
import com.rota.facil.security.exceptions.UserNotFoundExceptions;
import com.rota.facil.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RotaFacilUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String accessToken) throws UsernameNotFoundException {
        return new AuthenticatedUser(
                userRepository.findByEmail(accessToken)
                        .orElseThrow(UserNotFoundExceptions::new)
        );
    }
}
