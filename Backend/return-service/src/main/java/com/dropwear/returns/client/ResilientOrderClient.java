package com.dropwear.returns.client;

import java.util.UUID;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.dropwear.returns.web.DependencyUnavailableException;

/** S3 protégé : timeout 2 s -> retry x2 -> circuit breaker « order » -> fallback 503 (aucune RMA sans vérification). */
@Component
public class ResilientOrderClient {

    private final OrderClient client;

    public ResilientOrderClient(OrderClient client) {
        this.client = client;
    }

    @Retry(name = "order")
    @CircuitBreaker(name = "order", fallbackMethod = "getOrderFallback")
    public OrderClient.OrderInfo getOrder(UUID orderId) {
        return client.getOrder(orderId);
    }

    private OrderClient.OrderInfo getOrderFallback(UUID orderId, Throwable cause) {
        if (cause instanceof FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande introuvable");
        }
        throw new DependencyUnavailableException("order-service", cause);
    }
}
