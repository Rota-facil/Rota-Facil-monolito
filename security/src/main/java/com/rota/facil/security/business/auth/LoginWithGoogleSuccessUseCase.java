package com.rota.facil.security.business.auth;

import com.rota.facil.annotations.UseCase;
import com.rota.facil.security.business.auth.helpers.FindUserTokenByUserIdHelper;
import com.rota.facil.security.persistence.entities.TokenCompleteGoogleLoginEntity;
import com.rota.facil.security.persistence.entities.UserTokenEntity;
import com.rota.facil.security.persistence.repositories.TokenCompleteGoogleLoginRepository;
import com.rota.facil.security.persistence.repositories.UserTokenRepository;
import com.rota.facil.security.service.JWTService;
import com.rota.facil.users.domain.Role;
import com.rota.facil.users.persistence.entities.UserEntity;
import com.rota.facil.users.persistence.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class LoginWithGoogleSuccessUseCase implements AuthenticationSuccessHandler {
    private final FindUserTokenByUserIdHelper findUserTokenByUserIdHelper;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final TokenCompleteGoogleLoginRepository tokenCompleteGoogleLoginRepository;
    private final JWTService jwtService;

    @Value("${web.base.url}")
    private String WEB_BASE_URL;

    @Value("${app.base.url}")
    private String APP_BASE_URL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User googleUser = oAuth2AuthenticationToken.getPrincipal();

        switch (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId()) {
            case "google-student" -> this.studentLogin(googleUser, response);
            case "google-prefecture" -> this.prefectureLogin(googleUser, response);
        }
    }

    private void studentLogin(OAuth2User googleUser, HttpServletResponse response)  throws IOException, ServletException  {
        String email = googleUser.getAttribute("email");
        String name =  googleUser.getAttribute("name");

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    return userRepository.save(
                            UserEntity.builder()
                                    .email(email)
                                    .name(name)
                                    .role(Role.STUDENT)
                                    .build()
                    );
                });

        response.setContentType("application/json");

        if (user.getPrefectureId() != null && user.getCpf() != null) {
            UserTokenEntity userToken = this.findUserTokenByUserIdHelper.execute(user.getId());

            String accessToken = jwtService.generateAccessToken(user);

            userToken.setAccessToken(accessToken);

            this.userTokenRepository.save(userToken);

            response.sendRedirect(APP_BASE_URL + "/oauth2/callback?token=" + accessToken);
            return;
        }


        UUID pendingToken = UUID.randomUUID();
        tokenCompleteGoogleLoginRepository.findByUserId(user.getId())
                .map(token -> {
                    token.setPendingToken(pendingToken);
                    return tokenCompleteGoogleLoginRepository.save(token);
                })
                .orElseGet(() -> tokenCompleteGoogleLoginRepository.save(
                        TokenCompleteGoogleLoginEntity.builder()
                                .user(user)
                                .pendingToken(pendingToken)
                                .build()
                ));
        response.sendRedirect(APP_BASE_URL + "/complete-login?token=" + pendingToken);

    }

    private void prefectureLogin(OAuth2User googleUser, HttpServletResponse response)  throws IOException, ServletException  {
        String email = googleUser.getAttribute("email");

        UserEntity user = userRepository.findByEmail(email).orElse(null);
        response.setContentType("application/json");

        if (user != null && user.getRole().equals(Role.ADMIN)) {
            String accessToken = jwtService.generateAccessToken(user);
            response.sendRedirect(WEB_BASE_URL + "/oauth2/callback?token=" + accessToken);
            return;
        }
        response.sendRedirect(WEB_BASE_URL + "/login");

    }
}
