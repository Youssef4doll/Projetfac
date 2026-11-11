package com.dropwear.returns.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E10 — topic « retour.recu ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record RetourRecuEvent(UUID rmaId, String rmaNumber, UUID orderId, String customerId, String type, List<Item> items, BigDecimal refundAmount) {

    public record Item(String sku, int quantity, BigDecimal unitPrice, String condition, String replacementSku, UUID replacementReservationId) {
    }
}
