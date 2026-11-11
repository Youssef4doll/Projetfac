package com.dropwear.fulfillment.web;

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

import com.dropwear.fulfillment.billing.domain.*;
import com.dropwear.fulfillment.domain.*;
import com.dropwear.fulfillment.logistics.domain.*;
import com.dropwear.fulfillment.web.dto.FulfillmentDtos.*;

/**
 * Contrat d'API /api/fulfillment/shipments (fulfillment-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/fulfillment/shipments")
public class ShipmentController {

    /** GET /api/fulfillment/shipments — rôle : ADMIN — codes : 200, 403 */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ShipmentDto>> listShipments(
            @RequestParam(required = false) ShipmentStatus status,
            @RequestParam(required = false) UUID orderId,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }

    /** GET /api/fulfillment/shipments/mine — rôle : FOURNISSEUR — codes : 200, 403 */
    @GetMapping("/mine")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<List<ShipmentDto>> myShipments(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) ShipmentStatus status) {
        throw Stubs.notImplemented();
    }

    /** GET /api/fulfillment/shipments/{shipmentId} — rôle : ADMIN, FOURNISSEUR — codes : 200, 403, 404 */
    @GetMapping("/{shipmentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FOURNISSEUR')")
    public ResponseEntity<ShipmentDto> getShipment(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID shipmentId) {
        throw Stubs.notImplemented();
    }

    /** GET /api/fulfillment/shipments/{shipmentId}/label — rôle : ADMIN, FOURNISSEUR — codes : 200 (application/pdf), 403, 404 */
    @GetMapping(value = "/{shipmentId}/label", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'FOURNISSEUR')")
    public ResponseEntity<byte[]> getLabel(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID shipmentId) {
        throw Stubs.notImplemented();
    }

    /** POST /api/fulfillment/shipments/{shipmentId}/ship — rôle : FOURNISSEUR, ADMIN — codes : 200, 400, 403, 404, 409 — publie E6 quand toutes les expéditions sont parties */
    @PostMapping("/{shipmentId}/ship")
    @PreAuthorize("hasAnyRole('FOURNISSEUR', 'ADMIN')")
    public ResponseEntity<ShipmentDto> ship(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID shipmentId,
            @Valid @RequestBody ShipRequest request) {
        throw Stubs.notImplemented();
    }
}
