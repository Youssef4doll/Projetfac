package com.dropwear.user.keycloak;

import java.util.UUID;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.dropwear.user.config.ServiceTokenConfig.ServiceTokenProvider;
import com.dropwear.user.domain.Role;
import com.dropwear.user.web.DependencyUnavailableException;

/**
 * S7 — user-service -> API d'administration Keycloak (compte de service avec realm-management : manage-users).
 * Pas de retry sur la création (non idempotente). Circuit breaker « keycloak-admin » -> fallback 503.
 * Compensation : si l'insertion du profil local échoue après createUser, appeler deleteUser.
 */
@Component
public class KeycloakAdminClient {

    private final RestClient restClient;
    private final ServiceTokenProvider tokens;

    public KeycloakAdminClient(RestClient.Builder builder, ServiceTokenProvider tokens,
                               @Value("${dropwear.keycloak.admin-url}") String adminUrl) {
        this.restClient = builder.baseUrl(adminUrl).build();
        this.tokens = tokens;
    }

    /** POST /users puis lecture de l'en-tête Location : renvoie l'id Keycloak (= sub = UserProfile.id). */
    @CircuitBreaker(name = "keycloak-admin", fallbackMethod = "unavailable")
    public UUID createUser(String email, String password, String firstName, String lastName) {
        // Validation 2 : restClient.post().uri("/users").header("Authorization", "Bearer " + tokens.token())...
        throw new UnsupportedOperationException("Validation 2 : POST /users");
    }

    /** POST /users/{id}/role-mappings/realm */
    @CircuitBreaker(name = "keycloak-admin", fallbackMethod = "unavailableVoid")
    public void assignRealmRole(UUID userId, Role role) {
        throw new UnsupportedOperationException("Validation 2");
    }

    /** PUT /users/{id} { "enabled": false } */
    @CircuitBreaker(name = "keycloak-admin", fallbackMethod = "unavailableVoid")
    public void disableUser(UUID userId) {
        throw new UnsupportedOperationException("Validation 2");
    }

    /** DELETE /users/{id} : compensation. */
    @CircuitBreaker(name = "keycloak-admin", fallbackMethod = "unavailableVoid")
    public void deleteUser(UUID userId) {
        throw new UnsupportedOperationException("Validation 2");
    }

    private UUID unavailable(String email, String password, String firstName, String lastName, Throwable cause) {
        throw new DependencyUnavailableException("keycloak", cause);
    }

    private void unavailableVoid(UUID userId, Throwable cause) {
        throw new DependencyUnavailableException("keycloak", cause);
    }

    private void unavailableVoid(UUID userId, Role role, Throwable cause) {
        throw new DependencyUnavailableException("keycloak", cause);
    }
}
