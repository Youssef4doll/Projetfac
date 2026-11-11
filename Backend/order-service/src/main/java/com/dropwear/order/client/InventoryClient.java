package com.dropwear.order.client;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/** S2 — order-service -> inventory-service : réservation temporaire du stock (verrouillage). */
@FeignClient(name = "inventory-service", path = "/internal/inventory")
public interface InventoryClient {

    @PostMapping("/reservations")
    ReservationResponse reserve(@RequestHeader("Idempotency-Key") String idempotencyKey,
                                @RequestBody ReservationRequest request);

    @DeleteMapping("/reservations/{reservationId}")
    void release(@PathVariable("reservationId") UUID reservationId);

    record ReservationRequest(String referenceType, String referenceId, List<Line> lines, int ttlMinutes) {
        public record Line(String sku, int quantity) {
        }
    }

    record ReservationResponse(UUID reservationId, String status, Instant expiresAt) {
    }
}
