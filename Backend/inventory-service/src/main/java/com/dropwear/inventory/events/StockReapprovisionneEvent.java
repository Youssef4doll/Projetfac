package com.dropwear.inventory.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E5 — topic « stock.reapprovisionne ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record StockReapprovisionneEvent(String sku, String productId, int available) {
}
