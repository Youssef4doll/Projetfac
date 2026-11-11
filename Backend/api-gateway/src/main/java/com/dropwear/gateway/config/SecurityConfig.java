package com.dropwear.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Premier contrôle du JWT (signature, expiration, émetteur) avant tout routage.
 * Les règles fines par rôle restent dans chaque service (@PreAuthorize).
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/actuator/health/**", "/actuator/info").permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/catalog/**", "/api/inventory/stocks/**").permitAll()
                .pathMatchers("/ws/**").permitAll()          // JWT vérifié dans la trame STOMP CONNECT
                .pathMatchers("/internal/**").denyAll()      // jamais exposé à l'extérieur
                .anyExchange().authenticated())
            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
