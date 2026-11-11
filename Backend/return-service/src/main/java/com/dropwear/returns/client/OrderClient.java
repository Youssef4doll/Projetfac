package com.dropwear.returns.client;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** S3 — return-service -> order-service : données faisant foi pour l'éligibilité (14 jours après livraison). */
@FeignClient(name = "order-service", path = "/internal/orders")
public interface OrderClient {

    @GetMapping("/{orderId}")
    OrderInfo getOrder(@PathVariable("orderId") UUID orderId);

    record OrderInfo(UUID orderId, String orderNumber, String customerId, String status, Instant deliveredAt, List<Line> lines) {
        public record Line(String sku, String productName, int quantity, BigDecimal unitPrice) {
        }
    }
}
