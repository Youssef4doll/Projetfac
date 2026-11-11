package com.dropwear.catalog.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * E1 — topic « catalogue.variante-creee ».
 * Copie locale du contrat (pas de bibliothèque partagée entre services : lecteur tolérant, JSON sans en-tête de type).
 */
public record VarianteCreeeEvent(String productId, String sku, UUID supplierId, String size, String fit, String color) {
}
