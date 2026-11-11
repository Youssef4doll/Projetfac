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

/** Une variante = un SKU = taille x coupe x coloris. lowStock/outOfStock : vue locale alimentée par E4/E5. */
@Getter
@Setter
@NoArgsConstructor
public class Variant {

    private String sku;

    private Size size;

    private Fit fit;

    private String color;

    private String colorHex;

    private BigDecimal priceOverride;

    private String supplierSku;

    private boolean lowStock;

    private boolean outOfStock;

    private boolean active;
}
