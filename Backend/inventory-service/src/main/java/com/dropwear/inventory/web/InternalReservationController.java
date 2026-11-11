package com.dropwear.inventory.web;

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

import com.dropwear.inventory.domain.*;
import com.dropwear.inventory.web.dto.InventoryDtos.*;

/**
 * Contrat d'API /internal/inventory/reservations (inventory-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/internal/inventory/reservations")
public class InternalReservationController {

    /** POST /internal/inventory/reservations — rôle : SERVICE — codes : 201, 200 (rejeu idempotent), 400, 409 {unavailableSkus} — appelé par S2 et S4 */
    @PostMapping
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<ReservationDto> reserve(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody ReservationRequest request) {
        throw Stubs.notImplemented();
    }

    /** GET /internal/inventory/reservations/{reservationId} — rôle : SERVICE — codes : 200, 404 */
    @GetMapping("/{reservationId}")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable UUID reservationId) {
        throw Stubs.notImplemented();
    }

    /** DELETE /internal/inventory/reservations/{reservationId} — rôle : SERVICE — codes : 204, 404, 409 (déjà confirmée) */
    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<Void> release(@PathVariable UUID reservationId) {
        throw Stubs.notImplemented();
    }
}
