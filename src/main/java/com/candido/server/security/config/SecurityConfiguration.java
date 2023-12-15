package com.candido.server.security.config;

import com.candido.server.domain.v1.account.PermissionEnum;
import com.candido.server.domain.v1.account.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        long MAX_AGE_SECS = 3600;

        configuration.addAllowedOriginPattern("*");
        configuration.setExposedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setMaxAge(MAX_AGE_SECS);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Disabilito il Cross-Site Request Forgery
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                        .configurationSource(corsConfigurationSource())
                )
                .authorizeHttpRequests(auth -> auth
                        // Abilita una whitelist di url
                        .requestMatchers("/api/v1/auth/**", "/oauth2/**", "/",
                                "/error",
                                "/favicon.ico")
                        .permitAll()

                        // Imposto la sicurezza per i path
                        .requestMatchers("/api/v1/admin/**").hasAnyRole(RoleEnum.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAuthority(PermissionEnum.ADMIN_READ.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasAuthority(PermissionEnum.ADMIN_CREATE.name())

                        // Per tutti gli altri url bisogna essere autenticati
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Forniamo AuthenticationProvider creato in ApplicationConfig
                .authenticationProvider(authenticationProvider)
                // Prima di tutto utilizziamo il filtro creato in JwtAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Aggiungo il path del logout con il suo handler
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                )
                // Imposto come valore di status il 401 quando fallisce l'autenticazione
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                );


        return httpSecurity.build();
    }

}
