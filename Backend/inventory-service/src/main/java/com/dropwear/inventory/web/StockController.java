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
 * Contrat d'API /api/inventory/stocks (inventory-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/inventory/stocks")
public class StockController {

    /** GET /api/inventory/stocks — rôle : public — codes : 200, 400 */
    @GetMapping
    public ResponseEntity<List<StockDto>> getStocks(@RequestParam List<String> skus) {
        throw Stubs.notImplemented();
    }

    /** GET /api/inventory/stocks/mine — rôle : FOURNISSEUR — codes : 200, 403 */
    @GetMapping("/mine")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<List<StockItemDto>> myStocks(@AuthenticationPrincipal Jwt jwt) {
        throw Stubs.notImplemented();
    }

    /** GET /api/inventory/stocks/{sku} — rôle : public — codes : 200, 404 */
    @GetMapping("/{sku}")
    public ResponseEntity<StockDto> getStock(@PathVariable String sku) {
        throw Stubs.notImplemented();
    }

    /** PUT /api/inventory/stocks/{sku} — rôle : FOURNISSEUR, ADMIN — codes : 200, 400, 403, 404, 409 (inférieur au réservé) */
    @PutMapping("/{sku}")
    @PreAuthorize("hasAnyRole('FOURNISSEUR', 'ADMIN')")
    public ResponseEntity<StockItemDto> updateStock(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String sku,
            @Valid @RequestBody StockUpdateRequest request) {
        throw Stubs.notImplemented();
    }

    /** PATCH /api/inventory/stocks/{sku}/threshold — rôle : ADMIN — codes : 200, 400, 404 */
    @PatchMapping("/{sku}/threshold")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StockItemDto> updateThreshold(
            @PathVariable String sku,
            @Valid @RequestBody ThresholdRequest request) {
        throw Stubs.notImplemented();
    }
}
