package com.dropwear.catalog.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E4 — topic « stock.faible ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record StockFaibleEvent(String sku, String productId, UUID supplierId, int available, int threshold) {
}
