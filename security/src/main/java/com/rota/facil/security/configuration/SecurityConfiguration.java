package com.rota.facil.security.configuration;

import com.rota.facil.security.filters.RotaFacilAuthenticationFilter;
import com.rota.facil.security.service.RotaFacilUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final RotaFacilUserDetailsService rotaFacilUserDetailsService;
    private final RotaFacilAuthenticationFilter rotaFacilAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.csrf(CsrfConfigurer::disable)
                .addFilterBefore(rotaFacilAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/webjars/**", "/*/v3/api-docs").permitAll()
                            .requestMatchers("/*/v3/api-docs/**", "/*/swagger-ui/**", "/*/swagger-ui.html").permitAll()
                            .requestMatchers("/swagger-ui.html").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS).permitAll()
                            .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                            .requestMatchers("/auth/login", "/auth/register").permitAll()

                            .requestMatchers(HttpMethod.GET, "/prefectures/**").permitAll()
                            .requestMatchers("/prefectures/**").hasRole("SUPERUSER")

                            .requestMatchers(HttpMethod.GET, "/board-points/**", "/institutions/**", "/routes/**")
                                .authenticated()
                            .requestMatchers(HttpMethod.POST, "/board-points", "/institutions")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.PUT, "/board-points/**", "/institutions/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.DELETE, "/board-points/**", "/institutions/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")

                            .requestMatchers(HttpMethod.POST, "/routes")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.PUT, "/routes/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.DELETE, "/routes/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")

                            .requestMatchers(HttpMethod.GET, "/vehicles", "/vehicles/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.POST, "/vehicles", "/vehicles/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.PUT, "/vehicles/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.DELETE, "/vehicles/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")

                            .requestMatchers(HttpMethod.POST, "/trips")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.POST, "/trips/*/checkin", "/trips/*/join")
                                .hasRole("STUDENT")
                            .requestMatchers(HttpMethod.POST, "/trips/*/init", "/trips/*/return", "/trips/*/cancel")
                                .hasRole("DRIVER")
                            .requestMatchers(HttpMethod.GET, "/trips/my-today")
                                .hasAnyRole("STUDENT", "DRIVER")
                            .requestMatchers(HttpMethod.GET, "/trips/*/students")
                                .hasRole("DRIVER")

                            .requestMatchers("/feedbacks/**")
                                .hasAnyRole("STUDENT", "DRIVER", "ADMIN", "SUPERUSER")
                            .requestMatchers("/vehicles/*/photos/**")
                                .hasAnyRole("ADMIN", "SUPERUSER")
                            .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(rotaFacilUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(this.bCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
