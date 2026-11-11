package com.dropwear.order.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.dropwear.order.domain.*;
import com.dropwear.order.web.dto.OrderDtos.*;

/**
 * Contrat d'API /api/orders (order-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    /** POST /api/orders — rôle : CLIENT — codes : 201, 400 (panier vide) */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<OrderDto> createOrder(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateOrderRequest request) {
        throw Stubs.notImplemented();
    }

    /** POST /api/orders/{orderId}/validate — rôle : CLIENT — codes : 200, 403, 404, 409 (stock insuffisant), 503 — appelle S2, publie E2 */
    @PostMapping("/{orderId}/validate")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<OrderDto> validateOrder(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID orderId) {
        throw Stubs.notImplemented();
    }

    /** POST /api/orders/{orderId}/cancel — rôle : CLIENT, ADMIN — codes : 200, 403, 404, 409 (déjà expédiée) — publie E3 */
    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<OrderDto> cancelOrder(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID orderId,
            @Valid @RequestBody CancelOrderRequest request) {
        throw Stubs.notImplemented();
    }

    /** GET /api/orders/me — rôle : CLIENT — codes : 200 */
    @GetMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Page<OrderSummaryDto>> myOrders(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) OrderStatus status,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }

    /** GET /api/orders/{orderId} — rôle : CLIENT, ADMIN, SUPPORT — codes : 200, 403, 404 */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN', 'SUPPORT')")
    public ResponseEntity<OrderDto> getOrder(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID orderId) {
        throw Stubs.notImplemented();
    }

    /** GET /api/orders/{orderId}/history — rôle : CLIENT, ADMIN, SUPPORT — codes : 200, 404 */
    @GetMapping("/{orderId}/history")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN', 'SUPPORT')")
    public ResponseEntity<List<OrderStatusHistoryDto>> getHistory(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID orderId) {
        throw Stubs.notImplemented();
    }

    /** GET /api/orders — rôle : ADMIN, SUPPORT — codes : 200, 403 */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<Page<OrderSummaryDto>> listOrders(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }
}
