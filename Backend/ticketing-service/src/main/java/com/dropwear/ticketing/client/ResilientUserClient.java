package com.dropwear.ticketing.client;

import java.util.Optional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;

/** S6 protégé, mode dégradé : sans coordonnées, le ticket est créé avec customerId seul et enrichi plus tard. */
@Component
public class ResilientUserClient {

    private final UserClient client;

    public ResilientUserClient(UserClient client) {
        this.client = client;
    }

    @Retry(name = "user")
    @CircuitBreaker(name = "user", fallbackMethod = "getContactFallback")
    public Optional<UserClient.UserContact> getContact(String userId) {
        return Optional.of(client.getContact(userId));
    }

    private Optional<UserClient.UserContact> getContactFallback(String userId, Throwable cause) {
        return Optional.empty();
    }
}
