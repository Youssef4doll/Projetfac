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
 * Contrat d'API /api/cart (order-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    /** GET /api/cart — rôle : CLIENT — codes : 200, 401 */
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal Jwt jwt) {
        throw Stubs.notImplemented();
    }

    /** POST /api/cart/items — rôle : CLIENT — codes : 201, 400, 404 (SKU inconnu), 503 (catalogue indisponible) — appelle S1 */
    @PostMapping("/items")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartDto> addItem(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AddCartItemRequest request) {
        throw Stubs.notImplemented();
    }

    /** PATCH /api/cart/items/{itemId} — rôle : CLIENT — codes : 200, 400, 404 */
    @PatchMapping("/items/{itemId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<CartDto> updateItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        throw Stubs.notImplemented();
    }

    /** DELETE /api/cart/items/{itemId} — rôle : CLIENT — codes : 204, 404 */
    @DeleteMapping("/items/{itemId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> removeItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID itemId) {
        throw Stubs.notImplemented();
    }

    /** DELETE /api/cart — rôle : CLIENT — codes : 204 */
    @DeleteMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal Jwt jwt) {
        throw Stubs.notImplemented();
    }
}
