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
 * Contrat d'API /api/fulfillment/payments (fulfillment-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/fulfillment/payments")
public class PaymentController {

    /** GET /api/fulfillment/payments — rôle : ADMIN — codes : 200, 403 */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PaymentDto>> listPayments(
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(required = false) PaymentMethod method,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }

    /** POST /api/fulfillment/payments/{paymentId}/collect — rôle : ADMIN — codes : 200, 400, 404, 409 (déjà payé) */
    @PostMapping("/{paymentId}/collect")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentDto> collect(
            @PathVariable UUID paymentId,
            @Valid @RequestBody CollectRequest request) {
        throw Stubs.notImplemented();
    }
}
