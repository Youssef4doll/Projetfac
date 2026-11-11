package com.dropwear.returns.web.dto;

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
import com.dropwear.returns.domain.*;

/** Contrats d'échange REST de return-service (records immuables). */
public final class ReturnDtos {

    private ReturnDtos() {
    }

    public record EligibleItemDto(String sku, String productName, int quantity) {
    }

    public record EligibilityDto(boolean eligible, Instant deadline, List<EligibleItemDto> eligibleItems, String reason) {
    }

    public record ReturnItemRequest(@NotBlank String sku, @Positive int quantity, String replacementSku) {
    }

    public record CreateReturnRequest(@NotNull UUID orderId, @NotNull ReturnType type, @NotNull ReturnReason reason, String comment, String ticketId, @NotEmpty @Valid List<ReturnItemRequest> items) {
    }

    public record ReturnItemDto(String sku, String productName, int quantity, BigDecimal unitPrice, ItemCondition condition, String replacementSku) {
    }

    public record ReturnLabelDto(String carrier, String trackingNumber, String labelUrl, Instant issuedAt, Instant expiresAt) {
    }

    public record ReturnDto(UUID id, String rmaNumber, UUID orderId, String customerId, ReturnType type, ReturnStatus status, ReturnReason reason, String comment, Instant eligibilityDeadline, ReturnOutcome outcome, List<ReturnItemDto> items, ReturnLabelDto label, Instant createdAt) {
    }

    public record ReturnSummaryDto(UUID id, String rmaNumber, UUID orderId, ReturnType type, ReturnStatus status, Instant createdAt) {
    }

    public record DecisionRequest(String comment) {
    }

    public record RejectRequest(@NotBlank String reason) {
    }

    public record ReceivedItemDto(@NotBlank String sku, @NotNull ItemCondition condition) {
    }

    public record ReceiveRequest(@NotEmpty @Valid List<ReceivedItemDto> items) {
    }
}
