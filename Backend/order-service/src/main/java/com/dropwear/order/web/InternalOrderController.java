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
 * Contrat d'API /internal/orders (order-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/internal/orders")
public class InternalOrderController {

    /** GET /internal/orders/{orderId} — rôle : SERVICE — codes : 200, 404 — appelé par return-service (S3) et ticketing-service (S5) */
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('SERVICE')")
    public ResponseEntity<InternalOrderDto> getOrderForService(@PathVariable UUID orderId) {
        throw Stubs.notImplemented();
    }
}
