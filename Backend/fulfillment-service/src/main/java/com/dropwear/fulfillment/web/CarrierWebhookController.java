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
 * Contrat d'API /api/fulfillment/carrier-events (fulfillment-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/fulfillment/carrier-events")
public class CarrierWebhookController {

    /** POST /api/fulfillment/carrier-events — rôle : TRANSPORTEUR — codes : 202, 400, 401, 404 (n° de suivi inconnu) — peut publier E7 / E8 */
    @PostMapping
    @PreAuthorize("hasRole('TRANSPORTEUR')")
    public ResponseEntity<Void> receiveCarrierEvent(@Valid @RequestBody CarrierEventRequest request) {
        throw Stubs.notImplemented();
    }
}
