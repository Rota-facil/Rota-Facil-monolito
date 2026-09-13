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
                            .requestMatchers(HttpMethod.OPTIONS).permitAll()
                            .requestMatchers("/actuator/**").permitAll()
                            .requestMatchers("/auth/health-check").permitAll()
                            .requestMatchers("/transports/health-check").permitAll()
                            .requestMatchers("/files/health-check").permitAll()
                            .requestMatchers("/places/health-check").permitAll()
                            .requestMatchers("/audit/health-check").permitAll()
                            .requestMatchers("/locations/health-check").permitAll()

                            .requestMatchers("/auth/user/login").permitAll()
                            .requestMatchers("/auth/register/**").permitAll()
                            .requestMatchers("/auth/google/complete-registration").permitAll()
                            .requestMatchers("/auth/login/oauth2/**").permitAll()
                            .requestMatchers("/auth/oauth2/**").permitAll()
                            .requestMatchers("/auth/auth/google/success").permitAll()

                            .requestMatchers(HttpMethod.GET, "/places/**").authenticated()
                            .requestMatchers(HttpMethod.GET, "/auth/prefectures/**").permitAll()

                            .requestMatchers("/places/**").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers("/audit/**").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers("/transports/metrics/**").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers("/transports/reports/**").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.GET, "/transports/feedbacks/users/**").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.POST, "/transports/routes/{routeId}/board-point/heat-map").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.GET, "/files/heat-map/{routeId}/all").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.DELETE, "/files/heat-map/{fileId}").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.GET, "/transports/institutions/route-counts").hasAnyRole("ADMIN", "SUPERUSER")

                            .requestMatchers("/auth/user/prefecture/register").hasRole("SUPERUSER")
                            .requestMatchers("/auth/prefectures/**").hasRole("SUPERUSER")


                            .requestMatchers(HttpMethod.GET, "/auth/students").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers("/auth/driver/register").hasRole("ADMIN")
                            .requestMatchers("/auth/user/prefecture/register").hasRole("ADMIN")

                            .requestMatchers("/transports/trips/{tripId}/join").hasAnyRole("STUDENT")
                            .requestMatchers("/transports/trips/{tripId}/exit").hasAnyRole("STUDENT")
                            .requestMatchers("/transports/trips/{tripId}/checkin").hasAnyRole("STUDENT")
                            .requestMatchers("/auth/user/prefecture/{prefectureId}/change").hasAnyRole("STUDENT")

                            .requestMatchers("/transports/trips/my-trips").hasAnyRole("STUDENT", "DRIVER")

                            .requestMatchers("/transports/trips/{tripId}/init").hasAnyRole("DRIVER")
                            .requestMatchers("/transports/trips/{tripId}/return/init").hasAnyRole("DRIVER")
                            .requestMatchers("/transports/trips/{tripId}/cancel").hasAnyRole("DRIVER")

                            .requestMatchers("/transports/routes/register").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/transports/routes/{routeId}/interpreter").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.GET, "/transports/routes/{routeId}/interpretations").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.DELETE, "/transports/routes/{routeId}/interpretations/{interpretationId}").hasAnyRole("ADMIN", "SUPERUSER")
                            .requestMatchers(HttpMethod.PUT, "/transports/routes/{routeId}").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/transports/routes/{routeId}").hasAnyRole("ADMIN")
                            .requestMatchers("/transports/trips/register").hasAnyRole("ADMIN")
                            .requestMatchers("/transports/bus/register").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/transports/bus/{busId}").hasAnyRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/transports/bus/{busId}").hasAnyRole("ADMIN")
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
