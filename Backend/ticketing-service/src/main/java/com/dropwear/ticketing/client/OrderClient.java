package com.dropwear.ticketing.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** S5 — ticketing-service -> order-service : vérifier que la commande appartient au client, contexte pour l'agent. */
@FeignClient(name = "order-service", path = "/internal/orders")
public interface OrderClient {

    @GetMapping("/{orderId}")
    OrderInfo getOrder(@PathVariable("orderId") UUID orderId);

    record OrderInfo(UUID orderId, String orderNumber, String customerId, String status) {
    }
}
