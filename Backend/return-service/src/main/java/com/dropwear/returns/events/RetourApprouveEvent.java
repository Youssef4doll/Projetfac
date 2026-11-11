package com.dropwear.returns.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E9 — topic « retour.approuve ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record RetourApprouveEvent(UUID rmaId, String rmaNumber, UUID orderId, String customerId, String type, String ticketId, String trackingNumber) {
}
