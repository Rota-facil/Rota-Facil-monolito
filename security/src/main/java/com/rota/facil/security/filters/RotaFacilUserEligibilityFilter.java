package com.rota.facil.security.filters;

import com.rota.facil.security.business.helpers.UserEligibilityValidatorHelper;
import com.rota.facil.security.user.details.AuthenticatedUser;
import com.rota.facil.users.persistence.entities.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RotaFacilUserEligibilityFilter extends OncePerRequestFilter {
    private final static AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    private final UserEligibilityValidatorHelper userEligibilityValidatorHelper;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) filterChain.doFilter(request, response);;

        UserEntity currentUser =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.isNotDriver() && currentUser.isNotStudent()) filterChain.doFilter(request, response);;

        try {
//            this.userEligibilityValidatorHelper.execute(currentUser);
            System.out.println();
        } catch (Exception e) {
            this.handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    e
            );
        }


        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        return !ANT_PATH_MATCHER.match("/trips/*/join", path) &&
               !ANT_PATH_MATCHER.match("/trips/*/init", path) &&
               !ANT_PATH_MATCHER.match("/trips/*/return", path) &&
               !ANT_PATH_MATCHER.match("/trips/*/cancel", path);
    }
}
