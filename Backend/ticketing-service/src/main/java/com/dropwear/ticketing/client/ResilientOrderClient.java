package com.dropwear.ticketing.client;

import java.util.Optional;
import java.util.UUID;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * S5 protégé, en MODE DÉGRADÉ : si order-service est indisponible, on renvoie Optional.empty()
 * et le ticket est quand même créé avec orderVerified = false (un client qui se plaint ne reçoit jamais d'erreur).
 */
@Component
public class ResilientOrderClient {

    private final OrderClient client;

    public ResilientOrderClient(OrderClient client) {
        this.client = client;
    }

    @Retry(name = "order")
    @CircuitBreaker(name = "order", fallbackMethod = "getOrderFallback")
    public Optional<OrderClient.OrderInfo> getOrder(UUID orderId) {
        return Optional.of(client.getOrder(orderId));
    }

    private Optional<OrderClient.OrderInfo> getOrderFallback(UUID orderId, Throwable cause) {
        if (cause instanceof FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande introuvable");
        }
        return Optional.empty();
    }
}
