package com.dropwear.fulfillment.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E11 — topic « retour.finalise ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record RetourFinaliseEvent(UUID rmaId, String outcome, UUID refundId, UUID shipmentId, String trackingNumber) {
}
