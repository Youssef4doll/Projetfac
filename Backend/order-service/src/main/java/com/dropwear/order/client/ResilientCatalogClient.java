package com.dropwear.order.client;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.dropwear.order.web.DependencyUnavailableException;

/** S1 protégé : timeout 2 s -> retry x2 -> circuit breaker « catalog » -> fallback 503 (article non ajouté). */
@Component
public class ResilientCatalogClient {

    private final CatalogClient client;

    public ResilientCatalogClient(CatalogClient client) {
        this.client = client;
    }

    @Retry(name = "catalog")
    @CircuitBreaker(name = "catalog", fallbackMethod = "getVariantFallback")
    public CatalogClient.VariantInfo getVariant(String sku) {
        return client.getVariant(sku);
    }

    private CatalogClient.VariantInfo getVariantFallback(String sku, Throwable cause) {
        if (cause instanceof FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SKU inconnu : " + sku);
        }
        // On ne fait jamais confiance au prix envoyé par le front : pas de valeur de repli.
        throw new DependencyUnavailableException("catalog-service", cause);
    }
}
