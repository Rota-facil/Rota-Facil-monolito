package com.rota.facil.security.business.auth;

import com.rota.facil.security.entities.UserTokenEntity;
import com.rota.facil.security.http.dto.request.user.AuthLoginRequest;
import com.rota.facil.security.http.dto.response.token.TokenResponse;
import com.rota.facil.security.service.JWTService;
import com.rota.facil.security.user.details.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public TokenResponse execute(AuthLoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) authenticationManager.authenticate(authentication).getPrincipal();

        UserTokenEntity userToken = jwtService.generateTokenForNewUser(authenticatedUser.getUser());

        return new TokenResponse(userToken.getAccessToken(), userToken.getRefreshToken());
    }
}
