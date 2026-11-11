package com.dropwear.returns.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import feign.RequestInterceptor;

/** Ajoute le jeton client_credentials (rôle SERVICE) à chaque appel OpenFeign vers /internal/**. */
@Configuration
public class FeignClientConfig {

    @Bean
    RequestInterceptor serviceTokenInterceptor(ServiceTokenConfig.ServiceTokenProvider tokens) {
        return template -> {
            String token = tokens.token();
            if (token != null) {
                template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            }
        };
    }
}
