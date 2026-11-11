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
 * Contrat d'API /api/catalog/categories (catalog-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/catalog/categories")
public class CategoryController {

    /** GET /api/catalog/categories — rôle : public — codes : 200 */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        throw Stubs.notImplemented();
    }

    /** POST /api/catalog/categories — rôle : ADMIN — codes : 201, 400, 409 */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryRequest request) {
        throw Stubs.notImplemented();
    }

    /** PUT /api/catalog/categories/{categoryId} — rôle : ADMIN — codes : 200, 400, 404 */
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable String categoryId,
            @Valid @RequestBody CategoryRequest request) {
        throw Stubs.notImplemented();
    }
}
