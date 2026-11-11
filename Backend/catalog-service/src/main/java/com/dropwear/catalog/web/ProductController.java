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
 * Contrat d'API /api/catalog/products (catalog-service).
 * Validation 1 : signatures, rôles et codes de statut figés ; corps à implémenter (501 en attendant).
 * L'identifiant de l'utilisateur courant est jwt.getSubject() (claim « sub » Keycloak).
 */
@RestController
@RequestMapping("/api/catalog/products")
public class ProductController {

    /** GET /api/catalog/products — rôle : public — codes : 200, 400 */
    @GetMapping
    public ResponseEntity<Page<ProductSummaryDto>> searchProducts(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) List<String> fits,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Pageable pageable) {
        throw Stubs.notImplemented();
    }

    /** GET /api/catalog/products/{productId} — rôle : public — codes : 200, 404 */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        throw Stubs.notImplemented();
    }

    /** POST /api/catalog/products — rôle : ADMIN — codes : 201, 400, 409 (slug existant) */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductRequest request) {
        throw Stubs.notImplemented();
    }

    /** PUT /api/catalog/products/{productId} — rôle : ADMIN — codes : 200, 400, 404 */
    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductRequest request) {
        throw Stubs.notImplemented();
    }

    /** PATCH /api/catalog/products/{productId}/status — rôle : ADMIN — codes : 200, 400, 404 */
    @PatchMapping("/{productId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> changeStatus(
            @PathVariable String productId,
            @Valid @RequestBody ProductStatusRequest request) {
        throw Stubs.notImplemented();
    }

    /** DELETE /api/catalog/products/{productId} — rôle : ADMIN — codes : 204, 404 */
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> archiveProduct(@PathVariable String productId) {
        throw Stubs.notImplemented();
    }

    /** POST /api/catalog/products/{productId}/variants — rôle : ADMIN — codes : 201, 400, 404, 409 (SKU existant) — publie E1 */
    @PostMapping("/{productId}/variants")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VariantDto> addVariant(
            @PathVariable String productId,
            @Valid @RequestBody VariantRequest request) {
        throw Stubs.notImplemented();
    }

    /** PUT /api/catalog/products/{productId}/variants/{sku} — rôle : ADMIN — codes : 200, 400, 404 */
    @PutMapping("/{productId}/variants/{sku}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VariantDto> updateVariant(
            @PathVariable String productId,
            @PathVariable String sku,
            @Valid @RequestBody VariantRequest request) {
        throw Stubs.notImplemented();
    }

    /** DELETE /api/catalog/products/{productId}/variants/{sku} — rôle : ADMIN — codes : 204, 404 */
    @DeleteMapping("/{productId}/variants/{sku}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateVariant(
            @PathVariable String productId,
            @PathVariable String sku) {
        throw Stubs.notImplemented();
    }

    /** POST /api/catalog/products/{productId}/images — rôle : ADMIN — codes : 201, 400, 404 */
    @PostMapping("/{productId}/images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductImageDto> addImage(
            @PathVariable String productId,
            @Valid @RequestBody ProductImageRequest request) {
        throw Stubs.notImplemented();
    }

    /** DELETE /api/catalog/products/{productId}/images/{imageId} — rôle : ADMIN — codes : 204, 404 */
    @DeleteMapping("/{productId}/images/{imageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteImage(
            @PathVariable String productId,
            @PathVariable String imageId) {
        throw Stubs.notImplemented();
    }
}
