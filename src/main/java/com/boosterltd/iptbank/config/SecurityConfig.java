package com.boosterltd.iptbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new HandmadePasswordEncoder();
    }
        /* -- Chain 1: Admin Endpoints -- */
    @Bean @Order(1)
    public SecurityFilterChain adminFilterChain(
        HttpSecurity http, AdminUserDetailsService adminUserDetailsService
    ) throws Exception {
        http
            .securityMatcher("/api/private-admin/**")
            .csrf((configurer) -> {
                    // Required only if you have a browser-based frontend calling this;
                    // For pure REST APIs, CSRF can be disabled.
                configurer.disable();
            })
            .authorizeHttpRequests((auth) -> {
                auth.anyRequest().hasRole("ADMIN");
            })
            .httpBasic(Customizer.withDefaults()) // Enable <Basic Authorization>.
            .userDetailsService(adminUserDetailsService);

        return http.build();
    }
        /* -- Chain 2: Client Endpoints -- */
    @Bean @Order(2)
    public SecurityFilterChain clientFilterChain(
        HttpSecurity http, ClientUserDetailsService clientUserDetailsService
    ) throws Exception {
        http
            .securityMatcher("/api/private-client/**")
            .csrf((configurer) -> {
                    // Required only if you have a browser-based frontend calling this;
                    // For pure REST APIs, CSRF can be disabled.
                configurer.disable();
            })
            .authorizeHttpRequests((auth) -> {
                auth.anyRequest().hasRole("CLIENT");
            })
            .httpBasic(Customizer.withDefaults()) // Enable <Basic Authorization>.
            .userDetailsService(clientUserDetailsService);

        return http.build();
    }
        /* -- Chain 3: Other Endpoints -- */
    @Bean @Order(3)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf((configurer) -> configurer.disable())
            .authorizeHttpRequests((auth) -> {
                auth.anyRequest().permitAll();
            });

        return http.build();
    }
}
