package com.rota.facil.security.filters;

import com.rota.facil.security.service.RotaFacilUserDetailsService;
import com.rota.facil.security.user.details.AuthenticatedUser;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RotaFacilIntelligenceCommunicationFilter extends OncePerRequestFilter {
    @Value("${x.api.key.intelligence}")
    private String API_KEY_INTELLIGENCE;
    private final static AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    private final RotaFacilUserDetailsService rotaFacilUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("x-api-key").equals(API_KEY_INTELLIGENCE)) {
            filterChain.doFilter(request, response);

            UserEntity adminRetrieved = objectMapper.readValue(
                    request.getInputStream(),
                    UserEntity.class
            );

            AuthenticatedUser authenticatedAndValidAdmin = (AuthenticatedUser) this.rotaFacilUserDetailsService.loadUserByUsername(adminRetrieved.getEmail());
            Authentication authentication = this.generateAuthentication(authenticatedAndValidAdmin);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return !ANT_PATH_MATCHER.match("/files/heat-map", path);
    }

    private Authentication generateAuthentication(AuthenticatedUser authenticatedUser) {
        return new UsernamePasswordAuthenticationToken(authenticatedUser.getUser(), "", authenticatedUser.getAuthorities());
    }
}
