package com.dropwear.returns.web;

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

import com.dropwear.returns.domain.*;
import com.dropwear.returns.web.dto.ReturnDtos.*;

/**
 * Contrat d'API /api/returns (return-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    /** GET /api/returns/eligibility — rôle : CLIENT — codes : 200, 403, 404, 503 — appelle S3 */
    @GetMapping("/eligibility")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<EligibilityDto> checkEligibility(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam UUID orderId) {
        throw Stubs.notImplemented();
    }

    /** POST /api/returns — rôle : CLIENT — codes : 201, 400, 403, 404, 409, 422 (hors délai / non livrée), 503 — appelle S3 (+ S4 si échange) */
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ReturnDto> createReturn(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateReturnRequest request) {
        throw Stubs.notImplemented();
    }

    /** GET /api/returns/me — rôle : CLIENT — codes : 200 */
    @GetMapping("/me")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReturnSummaryDto>> myReturns(@AuthenticationPrincipal Jwt jwt) {
        throw Stubs.notImplemented();
    }

    /** GET /api/returns — rôle : ADMIN, SUPPORT — codes : 200, 403 */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<Page<ReturnSummaryDto>> listReturns(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) ReturnStatus status,
            @RequestParam(required = false) ReturnType type,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }

    /** GET /api/returns/{rmaId} — rôle : CLIENT, ADMIN, SUPPORT — codes : 200, 403, 404 */
    @GetMapping("/{rmaId}")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN', 'SUPPORT')")
    public ResponseEntity<ReturnDto> getReturn(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID rmaId) {
        throw Stubs.notImplemented();
    }

    /** POST /api/returns/{rmaId}/approve — rôle : ADMIN, SUPPORT — codes : 200, 404, 409 — émet l'étiquette, publie E9 */
    @PostMapping("/{rmaId}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<ReturnDto> approve(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID rmaId,
            @Valid @RequestBody DecisionRequest request) {
        throw Stubs.notImplemented();
    }

    /** POST /api/returns/{rmaId}/reject — rôle : ADMIN, SUPPORT — codes : 200, 400, 404, 409 */
    @PostMapping("/{rmaId}/reject")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<ReturnDto> reject(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID rmaId,
            @Valid @RequestBody RejectRequest request) {
        throw Stubs.notImplemented();
    }

    /** GET /api/returns/{rmaId}/label — rôle : CLIENT, ADMIN — codes : 200, 403, 404 (pas encore émise) */
    @GetMapping("/{rmaId}/label")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    public ResponseEntity<ReturnLabelDto> getLabel(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID rmaId) {
        throw Stubs.notImplemented();
    }

    /** POST /api/returns/{rmaId}/receive — rôle : ADMIN, FOURNISSEUR — codes : 200, 400, 404, 409 — publie E10 */
    @PostMapping("/{rmaId}/receive")
    @PreAuthorize("hasAnyRole('ADMIN', 'FOURNISSEUR')")
    public ResponseEntity<ReturnDto> receive(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID rmaId,
            @Valid @RequestBody ReceiveRequest request) {
        throw Stubs.notImplemented();
    }
}
