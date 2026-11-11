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
 * Contrat d'API /api/users (user-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /** POST /api/users/register — rôle : public — codes : 201, 400, 409 (e-mail existant), 503 (Keycloak indisponible) */
    @PostMapping("/register")
    public ResponseEntity<UserProfileDto> register(@Valid @RequestBody RegisterRequest request) {
        throw Stubs.notImplemented();
    }

    /** GET /api/users/me — rôle : authentifié — codes : 200, 401 */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileDto> getMe(@AuthenticationPrincipal Jwt jwt) {
        throw Stubs.notImplemented();
    }

    /** PUT /api/users/me — rôle : authentifié — codes : 200, 400, 401 */
    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileDto> updateMe(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody UpdateProfileRequest request) {
        throw Stubs.notImplemented();
    }

    /** GET /api/users — rôle : ADMIN — codes : 200, 403 */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserProfileDto>> listUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) UserStatus status,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }

    /** GET /api/users/suppliers — rôle : ADMIN — codes : 200, 403 */
    @GetMapping("/suppliers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SupplierProfileDto>> listSuppliers() {
        throw Stubs.notImplemented();
    }

    /** GET /api/users/{userId} — rôle : ADMIN, SUPPORT — codes : 200, 403, 404 */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    public ResponseEntity<UserProfileDto> getUser(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID userId) {
        throw Stubs.notImplemented();
    }

    /** PATCH /api/users/{userId}/role — rôle : ADMIN — codes : 200, 400, 404, 503 */
    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileDto> changeRole(
            @PathVariable UUID userId,
            @Valid @RequestBody RoleUpdateRequest request) {
        throw Stubs.notImplemented();
    }

    /** PATCH /api/users/{userId}/status — rôle : ADMIN — codes : 200, 400, 404 */
    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserProfileDto> changeStatus(
            @PathVariable UUID userId,
            @Valid @RequestBody StatusUpdateRequest request) {
        throw Stubs.notImplemented();
    }

    /** POST /api/users/{userId}/supplier-profile — rôle : ADMIN — codes : 201, 400, 404, 409 */
    @PostMapping("/{userId}/supplier-profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SupplierProfileDto> createSupplierProfile(
            @PathVariable UUID userId,
            @Valid @RequestBody SupplierProfileRequest request) {
        throw Stubs.notImplemented();
    }
}
