package com.dropwear.ticketing.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E8 — topic « livraison.retardee ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record LivraisonRetardeeEvent(UUID orderId, String orderNumber, String customerId, UUID shipmentId, String trackingNumber, LocalDate expectedDate, int delayDays) {
}
