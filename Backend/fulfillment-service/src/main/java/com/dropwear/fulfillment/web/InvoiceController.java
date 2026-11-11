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
 * Contrat d'API /api/fulfillment/invoices (fulfillment-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/fulfillment/invoices")
public class InvoiceController {

    /** GET /api/fulfillment/invoices/me — rôle : CLIENT — codes : 200 */
    @GetMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<InvoiceSummaryDto>> myInvoices(@AuthenticationPrincipal Jwt jwt) {
        throw Stubs.notImplemented();
    }

    /** GET /api/fulfillment/invoices — rôle : ADMIN — codes : 200, 403 */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<InvoiceSummaryDto>> listInvoices(
            @RequestParam(required = false) InvoiceType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }

    /** GET /api/fulfillment/invoices/{invoiceId} — rôle : CLIENT, ADMIN — codes : 200, 403, 404 */
    @GetMapping("/{invoiceId}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<InvoiceDto> getInvoice(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID invoiceId) {
        throw Stubs.notImplemented();
    }

    /** GET /api/fulfillment/invoices/{invoiceId}/pdf — rôle : CLIENT, ADMIN — codes : 200 (application/pdf), 403, 404 */
    @GetMapping(value = "/{invoiceId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<byte[]> getInvoicePdf(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID invoiceId) {
        throw Stubs.notImplemented();
    }
}
