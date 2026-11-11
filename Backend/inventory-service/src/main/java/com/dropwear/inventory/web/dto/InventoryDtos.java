package com.dropwear.inventory.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.dropwear.inventory.domain.*;

/** Contrats d'échange REST de inventory-service (records immuables). */
public final class InventoryDtos {

    private InventoryDtos() {
    }

    public record StockDto(String sku, int available, boolean inStock) {
    }

    public record StockItemDto(String sku, String productId, UUID supplierId, int quantityOnHand, int quantityReserved, int available, int reorderThreshold, Instant updatedAt) {
    }

    public record StockUpdateRequest(@Min(0) int quantityOnHand) {
    }

    public record ThresholdRequest(@Min(0) int reorderThreshold) {
    }

    public record RestockAlertDto(UUID id, String sku, UUID supplierId, int availableAtAlert, int threshold, AlertStatus status, Instant createdAt) {
    }

    public record AlertUpdateRequest(@NotNull AlertStatus status) {
    }

    public record StockMovementDto(UUID id, String sku, MovementType type, int quantity, String referenceId, Instant createdAt) {
    }

    public record ReservationLineDto(@NotBlank String sku, @Positive int quantity) {
    }

    public record ReservationRequest(@NotNull ReservationType referenceType, @NotBlank String referenceId, @NotEmpty @Valid List<ReservationLineDto> lines, @Positive int ttlMinutes) {
    }

    public record ReservationDto(UUID reservationId, ReservationType referenceType, String referenceId, ReservationStatus status, Instant expiresAt, List<ReservationLineDto> lines) {
    }

    public record ReservationConflictDto(List<String> unavailableSkus) {
    }
}
