package com.FIA.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF (if necessary)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/**").permitAll()  // Allow all requests to /api/**
                .anyRequest().authenticated()  // Require authentication for other requests
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource)); // Apply CORS configuration

        return http.build();
    }
}
