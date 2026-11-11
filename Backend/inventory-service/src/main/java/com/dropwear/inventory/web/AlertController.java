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
 * Contrat d'API /api/inventory/alerts (inventory-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/inventory/alerts")
public class AlertController {

    /** GET /api/inventory/alerts — rôle : ADMIN, FOURNISSEUR — codes : 200, 403 */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FOURNISSEUR')")
    public ResponseEntity<List<RestockAlertDto>> listAlerts(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) AlertStatus status) {
        throw Stubs.notImplemented();
    }

    /** PATCH /api/inventory/alerts/{alertId} — rôle : ADMIN, FOURNISSEUR — codes : 200, 404 */
    @PatchMapping("/{alertId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FOURNISSEUR')")
    public ResponseEntity<RestockAlertDto> updateAlert(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID alertId,
            @Valid @RequestBody AlertUpdateRequest request) {
        throw Stubs.notImplemented();
    }
}
