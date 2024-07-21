package com.kafka.Implementation;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring HTTP Security...");

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/employees/**").authenticated()
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt()
                )
                .csrf().disable() // Disable CSRF for simplicity; enable it as needed
                .addFilterBefore(keycloakAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

        logger.info("HTTP Security configured.");
        return http.build();
    }

    @Bean
    public KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
        logger.info("Creating KeycloakAuthenticationProcessingFilter...");

        KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(authenticationManager());

        logger.info("KeycloakAuthenticationProcessingFilter created.");
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        logger.info("Creating AuthenticationManager...");

        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();

        // Create a ProviderManager with the KeycloakAuthenticationProvider
        AuthenticationManager authManager = new ProviderManager(keycloakAuthenticationProvider);

        logger.info("AuthenticationManager created.");
        return authManager;
    }


}
