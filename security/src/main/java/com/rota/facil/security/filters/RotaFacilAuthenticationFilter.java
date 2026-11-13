package com.rota.facil.security.filters;

import com.rota.facil.security.service.JWTService;
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
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RotaFacilAuthenticationFilter extends OncePerRequestFilter {
    @Value(value = "${x.api.key}")
    private String API_KEY;

    private final JWTService jwtService;
    private final RotaFacilUserDetailsService rotaFacilUserDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String email = null;



        if (request.getRequestURI().contains("/files/heat-map/") && request.getHeader("x-api-key").equals(API_KEY)) {
            filterChain.doFilter(request, response);

            UserEntity adminRetrieved = objectMapper.readValue(
                    request.getInputStream(),
                    UserEntity.class
            );

            AuthenticatedUser authenticatedAndValidAdmin = (AuthenticatedUser) this.rotaFacilUserDetailsService.loadUserByUsername(adminRetrieved.getEmail());
            Authentication authentication = this.generateAuthentication(authenticatedAndValidAdmin);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        if (authorization != null && authorization.startsWith("Bearer")) {
            token = authorization.substring(7);
            email = jwtService.extractEmail(token);
        }

        if (token != null && email != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) rotaFacilUserDetailsService.loadUserByUsername(email);

            if (jwtService.validateToken(token)) {
                Authentication authentication = this.generateAuthentication(authenticatedUser);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);
    }

    private Authentication generateAuthentication(AuthenticatedUser authenticatedUser) {
        return new UsernamePasswordAuthenticationToken(authenticatedUser.getUser(), "", authenticatedUser.getAuthorities());
    }
}
