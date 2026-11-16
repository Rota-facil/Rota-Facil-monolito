package com.rota.facil.security.configuration;

import com.rota.facil.security.business.useCases.auth.LoginWithGoogleSuccessUseCase;
import com.rota.facil.security.filters.RotaFacilAuthenticationFilter;
import com.rota.facil.security.filters.RotaFacilUserEligibilityFilter;
import com.rota.facil.security.service.RotaFacilUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    @Value("${web.base.url}")
    private String WEB_BASE_URL;

    @Value(value = "${app.base.url}")
    private String APP_BASE_URL;

    private final RotaFacilUserDetailsService rotaFacilUserDetailsService;
    private final RotaFacilAuthenticationFilter rotaFacilAuthenticationFilter;
    private final RotaFacilUserEligibilityFilter rotaFacilUserEligibilityFilter;
    private final LoginWithGoogleSuccessUseCase loginWithGoogleSuccessUseCase;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .oauth2Login(oauth2 -> oauth2.successHandler(loginWithGoogleSuccessUseCase))
                .exceptionHandling(
                        exception -> exception
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .addFilterBefore(this.rotaFacilAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(this.rotaFacilUserEligibilityFilter, RotaFacilAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/webjars/**", "/*/v3/api-docs").permitAll()
                            .requestMatchers("/*/v3/api-docs/**", "/*/swagger-ui/**", "/*/swagger-ui.html", "/oauth2/**").permitAll()
                            .requestMatchers("/swagger-ui.html").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS).permitAll()
                            .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                            .requestMatchers("/auth/login", "/auth/register", "/auth/complete/google").permitAll()
                            .requestMatchers(HttpMethod.GET, "/journey/metrics").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/prefectures/**").permitAll()
                            .requestMatchers("/prefectures/**").hasRole("SUPERUSER")

                            .requestMatchers(HttpMethod.GET, "/places/board-points/**", "/places/institutions/**", "/routes/**")
                                .authenticated()
                            .requestMatchers(HttpMethod.POST, "/places/board-points", "/places/institutions")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/places/board-points/**", "/places/institutions/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/places/board-points/**", "/places/institutions/**")
                                .hasRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/routes")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/routes/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/routes/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/routes/heat-map/**")
                                .authenticated()

                            .requestMatchers(HttpMethod.GET, "/vehicles", "/vehicles/**")
                                .authenticated()
                            .requestMatchers(HttpMethod.POST, "/vehicles", "/vehicles/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/vehicles/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/vehicles/**")
                                .hasRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/users/drivers")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/users/me")
                                .authenticated()
                            .requestMatchers(HttpMethod.PUT, "/users/update")
                                .authenticated()
                            .requestMatchers(HttpMethod.PATCH, "/users/*/change")
                                .hasRole("STUDENT")

                            .requestMatchers(HttpMethod.POST, "/trips")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/trips/*/checkin", "/trips/*/join")
                                .hasRole("STUDENT")
                            .requestMatchers(HttpMethod.POST, "/trips/*/init", "/trips/*/return", "/trips/*/cancel")
                                .hasAnyRole("ADMIN", "DRIVER")
                            .requestMatchers(HttpMethod.GET, "/trips/**")
                                .authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/trips/**")
                                .hasRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, "/feedbacks")
                                .hasAnyRole("STUDENT", "DRIVER")
                            .requestMatchers(HttpMethod.GET, "/feedbacks/user/**")
                                .hasRole("ADMIN")
                            .requestMatchers("/documents/enrollment-certificate")
                                .hasRole("STUDENT")
                            .requestMatchers(HttpMethod.GET, "/documents/enrollment-certificate/**")
                                .hasRole("ADMIN")
                            .requestMatchers("/documents/cnh")
                                .hasRole("DRIVER")
                            .requestMatchers(HttpMethod.GET, "/documents/cnh/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/documents/evaluate/**")
                                .hasRole("ADMIN")
                            .requestMatchers("/files/users/profile-pic")
                                .authenticated()
                            .requestMatchers(HttpMethod.POST, "/files/heat-map/**")
                                .authenticated()
                            .requestMatchers(HttpMethod.POST, "/files/institutions/**", "/files/board-points/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/files/institutions/**", "/files/board-points/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/files/institutions/**", "/files/board-points/**")
                                .authenticated()
                            .requestMatchers(HttpMethod.POST, "/files/vehicles/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/files/vehicles/**")
                                .hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/files/vehicles/**")
                                .authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/auth/logout", "/auth/deactivate")
                                .authenticated()
                            .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(
                List.of(
                        WEB_BASE_URL,
                        APP_BASE_URL
                )
        );

        corsConfiguration.setAllowedMethods(
                List.of(
                        "POST", "GET", "PUT",
                        "PATCH", "DELETE", "OPTIONS"
                )
        );

        corsConfiguration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return urlBasedCorsConfigurationSource;
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
