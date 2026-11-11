package com.dropwear.user.web;

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

import com.dropwear.user.domain.*;
import com.dropwear.user.web.dto.UserDtos.*;

/**
 * Contrat d'API /api/users/me/addresses (user-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/users/me/addresses")
public class AddressController {

    /** GET /api/users/me/addresses — rôle : authentifié — codes : 200, 401 */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AddressDto>> listAddresses(@AuthenticationPrincipal Jwt jwt) {
        throw Stubs.notImplemented();
    }

    /** POST /api/users/me/addresses — rôle : authentifié — codes : 201, 400 */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AddressDto> addAddress(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AddressRequest request) {
        throw Stubs.notImplemented();
    }

    /** PUT /api/users/me/addresses/{addressId} — rôle : authentifié — codes : 200, 400, 404 */
    @PutMapping("/{addressId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AddressDto> updateAddress(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID addressId,
            @Valid @RequestBody AddressRequest request) {
        throw Stubs.notImplemented();
    }

    /** DELETE /api/users/me/addresses/{addressId} — rôle : authentifié — codes : 204, 404 */
    @DeleteMapping("/{addressId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteAddress(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID addressId) {
        throw Stubs.notImplemented();
    }
}
