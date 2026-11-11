package com.dropwear.catalog.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.dropwear.catalog.domain.*;

/** Contrats d'échange REST de catalog-service (records immuables). */
public final class CatalogDtos {

    private CatalogDtos() {
    }

    public record ProductSummaryDto(String id, String name, String slug, BigDecimal basePrice, String currency, String mainImageUrl, List<String> availableSizes, List<String> availableColors, boolean lowStock) {
    }

    public record ProductDto(String id, String name, String slug, String description, String brand, String categoryId, UUID supplierId, BigDecimal basePrice, String currency, Gender gender, String material, List<String> tags, ProductStatus status, List<VariantDto> variants, List<ProductImageDto> images) {
    }

    public record ProductRequest(@NotBlank String name, @NotBlank String slug, String description, String brand, @NotBlank String categoryId, @NotNull UUID supplierId, @NotNull @Positive BigDecimal basePrice, String currency, Gender gender, String material, List<String> tags) {
    }

    public record ProductStatusRequest(@NotNull ProductStatus status) {
    }

    public record VariantRequest(@NotBlank String sku, @NotNull Size size, @NotNull Fit fit, @NotBlank String color, String colorHex, BigDecimal priceOverride, String supplierSku) {
    }

    public record VariantDto(String sku, Size size, Fit fit, String color, String colorHex, BigDecimal price, String supplierSku, boolean lowStock, boolean outOfStock, boolean active) {
    }

    public record ProductImageRequest(@NotBlank String url, String altText, String color, int position, boolean main) {
    }

    public record ProductImageDto(String imageId, String url, String altText, String color, int position, boolean main) {
    }

    public record CategoryRequest(@NotBlank String name, @NotBlank String slug, String parentId, String description) {
    }

    public record CategoryDto(String id, String name, String slug, String parentId, String description, boolean active, List<CategoryDto> children) {
    }

    public record VariantInfoDto(String sku, String productId, String productName, Size size, Fit fit, String color, BigDecimal unitPrice, String currency, UUID supplierId, boolean active) {
    }
}
