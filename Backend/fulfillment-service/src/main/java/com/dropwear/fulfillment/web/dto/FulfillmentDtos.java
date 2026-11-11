package com.dropwear.fulfillment.web.dto;

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
import com.dropwear.fulfillment.billing.domain.*;
import com.dropwear.fulfillment.domain.*;
import com.dropwear.fulfillment.logistics.domain.*;

/** Contrats d'échange REST de fulfillment-service (records immuables). */
public final class FulfillmentDtos {

    private FulfillmentDtos() {
    }

    public record ShipmentItemDto(String sku, String productName, int quantity) {
    }

    public record TrackingEventDto(ShipmentStatus status, String location, String description, Instant occurredAt) {
    }

    public record ShipmentDto(UUID id, UUID orderId, String orderNumber, UUID supplierId, ShipmentType type, ShipmentStatus status, String carrier, String trackingNumber, String labelUrl, LocalDate estimatedDeliveryDate, List<ShipmentItemDto> items, Instant shippedAt, Instant deliveredAt) {
    }

    public record ShipRequest(@NotBlank String carrier, @NotBlank String trackingNumber) {
    }

    public record ShipmentTrackingDto(UUID shipmentId, ShipmentStatus status, String carrier, String trackingNumber, List<TrackingEventDto> events) {
    }

    public record OrderTrackingDto(UUID orderId, List<ShipmentTrackingDto> shipments) {
    }

    public record CarrierEventRequest(@NotBlank String trackingNumber, @NotNull ShipmentStatus status, String location, @NotNull Instant occurredAt, Boolean codCollected, BigDecimal amountCollected) {
    }

    public record InvoiceSummaryDto(UUID id, String invoiceNumber, UUID orderId, InvoiceType type, InvoiceStatus status, LocalDate issueDate, BigDecimal totalAmount, String currency) {
    }

    public record InvoiceLineDto(String sku, String description, int quantity, BigDecimal unitPrice, BigDecimal lineTotal) {
    }

    public record InvoiceDto(UUID id, String invoiceNumber, UUID orderId, String customerId, InvoiceType type, InvoiceStatus status, LocalDate issueDate, BigDecimal subtotal, BigDecimal taxRate, BigDecimal taxAmount, BigDecimal totalAmount, String currency, List<InvoiceLineDto> lines) {
    }

    public record PaymentDto(UUID id, UUID orderId, PaymentMethod method, PaymentStatus status, BigDecimal amount, String currency, Instant paidAt) {
    }

    public record CollectRequest(@NotNull @Positive BigDecimal amountCollected, @NotBlank String collectedBy) {
    }

    public record RefundDto(UUID id, UUID rmaId, BigDecimal amount, RefundStatus status, Instant processedAt) {
    }
}
