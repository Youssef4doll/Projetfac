package com.dropwear.order.client;

import java.util.UUID;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;

import com.dropwear.order.web.BusinessConflictException;
import com.dropwear.order.web.DependencyUnavailableException;

/**
 * S2 protégé par Resilience4j : timeout Feign (3 s) -> retry x2 (sûr grâce à l'Idempotency-Key)
 * -> circuit breaker « inventory » -> fallback.
 * Fallback : la commande reste EN_ATTENTE, 503 « réessayez » (jamais de confirmation sans réservation).
 */
@Component
public class ResilientInventoryClient {

    private final InventoryClient client;

    public ResilientInventoryClient(InventoryClient client) {
        this.client = client;
    }

    @Retry(name = "inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "reserveFallback")
    public InventoryClient.ReservationResponse reserve(String idempotencyKey, InventoryClient.ReservationRequest request) {
        return client.reserve(idempotencyKey, request);
    }

    @Retry(name = "inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "releaseFallback")
    public void release(UUID reservationId) {
        client.release(reservationId);
    }

    private InventoryClient.ReservationResponse reserveFallback(String idempotencyKey,
                                                                InventoryClient.ReservationRequest request,
                                                                Throwable cause) {
        if (cause instanceof FeignException.Conflict conflict) {
            // 409 métier (stock insuffisant) : on le remonte tel quel, ce n'est pas une panne.
            throw new BusinessConflictException("Article(s) indisponible(s) : " + conflict.contentUTF8());
        }
        throw new DependencyUnavailableException("inventory-service", cause);
    }

    private void releaseFallback(UUID reservationId, Throwable cause) {
        // La réservation expirera d'elle-même (TTL) : compensation automatique.
        throw new DependencyUnavailableException("inventory-service", cause);
    }
}
