package com.dropwear.ticketing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

/**
 * Jeton « client credentials » du service (client Keycloak confidentiel, rôle SERVICE).
 * Utilisé pour les appels sortants, y compris ceux déclenchés par un événement Kafka (pas d'utilisateur).
 */
@Configuration
public class ServiceTokenConfig {

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository registrations,
                                                          OAuth2AuthorizedClientService clientService) {
        var manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(registrations, clientService);
        manager.setAuthorizedClientProvider(OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build());
        return manager;
    }

    @Bean
    ServiceTokenProvider serviceTokenProvider(OAuth2AuthorizedClientManager manager,
                                              @Value("${spring.application.name}") String applicationName) {
        return () -> {
            OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                    .principal(applicationName)
                    .build();
            OAuth2AuthorizedClient client = manager.authorize(request);
            return client == null ? null : client.getAccessToken().getTokenValue();
        };
    }

    /** Fournit un access token valide (mis en cache et renouvelé par Spring Security). */
    @FunctionalInterface
    public interface ServiceTokenProvider {
        String token();
    }
}
