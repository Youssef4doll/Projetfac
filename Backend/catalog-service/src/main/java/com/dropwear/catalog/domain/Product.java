package com.dropwear.catalog.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/** Document auto-portant : variantes et images imbriquées (lecture de la fiche produit en une requête). */
@Document(collection = "products")
@CompoundIndex(name = "variants_sku_unique", def = "{'variants.sku': 1}", unique = true, sparse = true)
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    @TextIndexed
    private String name;

    @Indexed(unique = true)
    private String slug;

    @TextIndexed
    private String description;

    private String brand;

    @Indexed
    private String categoryId;

    @Indexed
    private UUID supplierId;

    private BigDecimal basePrice;

    private String currency;

    private Gender gender;

    private String material;

    private List<String> tags = new ArrayList<>();

    private ProductStatus status;

    private List<Variant> variants = new ArrayList<>();

    private List<ProductImage> images = new ArrayList<>();

    private Instant createdAt;

    private Instant updatedAt;
}
