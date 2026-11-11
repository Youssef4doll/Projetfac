package com.dropwear.order.web.dto;

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
import com.dropwear.order.domain.*;

/** Contrats d'échange REST de order-service (records immuables). */
public final class OrderDtos {

    private OrderDtos() {
    }

    public record AddCartItemRequest(@NotBlank String sku, @Positive int quantity) {
    }

    public record UpdateCartItemRequest(@Positive int quantity) {
    }

    public record CartItemDto(UUID id, String sku, String productName, String size, String color, BigDecimal unitPrice, int quantity, BigDecimal lineTotal) {
    }

    public record CartDto(UUID id, List<CartItemDto> items, BigDecimal subtotal, String currency) {
    }

    public record ShippingAddressDto(@NotBlank String fullName, @NotBlank String street, @NotBlank String city, @NotBlank String postalCode, String governorate, @NotBlank String country, @NotBlank String phone) {
    }

    public record CreateOrderRequest(@NotNull @Valid ShippingAddressDto shippingAddress, @NotNull PaymentMethod paymentMethod) {
    }

    public record CancelOrderRequest(String reason) {
    }

    public record OrderLineDto(String sku, String productName, String size, String color, BigDecimal unitPrice, int quantity, BigDecimal lineTotal) {
    }

    public record OrderDto(UUID id, String orderNumber, String customerId, OrderStatus status, PaymentMethod paymentMethod, BigDecimal subtotal, BigDecimal shippingFee, BigDecimal totalAmount, String currency, ShippingAddressDto shippingAddress, List<OrderLineDto> lines, Instant createdAt, Instant confirmedAt, Instant shippedAt, Instant deliveredAt) {
    }

    public record OrderSummaryDto(UUID id, String orderNumber, OrderStatus status, BigDecimal totalAmount, String currency, Instant createdAt) {
    }

    public record OrderStatusHistoryDto(OrderStatus fromStatus, OrderStatus toStatus, Instant changedAt, String reason) {
    }

    public record InternalOrderLineDto(String sku, String productName, int quantity, BigDecimal unitPrice) {
    }

    public record InternalOrderDto(UUID orderId, String orderNumber, String customerId, OrderStatus status, Instant deliveredAt, List<InternalOrderLineDto> lines) {
    }
}
