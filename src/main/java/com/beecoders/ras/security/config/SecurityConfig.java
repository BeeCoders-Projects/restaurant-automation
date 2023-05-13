package com.beecoders.ras.security.config;

import com.beecoders.ras.security.entrypoint.SecurityAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityAuthenticationEntryPoint securityAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())
                .csrf().disable()
                .cors().configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration()
                            .applyPermitDefaultValues();
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addExposedHeader("Location");
                    corsConfiguration.addAllowedMethod(PATCH);
                    corsConfiguration.addAllowedMethod(PUT);
                    corsConfiguration.addAllowedMethod(DELETE);
                    return corsConfiguration;
                })
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers("/api-documentation/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(securityAuthenticationEntryPoint));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
