package com.rota.facil.security.filters;

import com.rota.facil.security.service.JWTService;
import com.rota.facil.security.service.RotaFacilUserDetailsService;
import com.rota.facil.security.user.details.AuthenticatedUser;
import com.rota.facil.users.entities.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RotaFacilAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final RotaFacilUserDetailsService rotaFacilUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;

        if (authorization.startsWith("Bearer")) {
            token = authorization.substring(7);
        }

        if (token != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) rotaFacilUserDetailsService.loadUserByUsername(token);
            UserEntity userEntity = authenticatedUser.getUser();

            if (jwtService.validateToken(token)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, "", authenticatedUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);
    }
}
