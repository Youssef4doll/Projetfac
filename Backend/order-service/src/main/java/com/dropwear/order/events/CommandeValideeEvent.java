package com.dropwear.order.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E2 — topic « commande.validee ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record CommandeValideeEvent(UUID orderId, String orderNumber, String customerId, UUID reservationId, String paymentMethod, BigDecimal totalAmount, String currency, ShippingAddress shippingAddress, List<Line> lines) {

    public record ShippingAddress(String fullName, String street, String city, String postalCode, String governorate, String country, String phone) {
    }

    public record Line(String sku, String productId, String productName, UUID supplierId, int quantity, BigDecimal unitPrice) {
    }
}
