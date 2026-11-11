package com.dropwear.catalog.web;

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

import com.dropwear.catalog.domain.*;
import com.dropwear.catalog.web.dto.CatalogDtos.*;

/**
 * Contrat d'API /internal/catalog (catalog-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/internal/catalog")
public class InternalCatalogController {

    /** GET /internal/catalog/variants/{sku} — rôle : SERVICE — codes : 200, 404 — appelé par order-service (S1) */
    @GetMapping("/variants/{sku}")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<VariantInfoDto> getVariant(@PathVariable String sku) {
        throw Stubs.notImplemented();
    }
}
