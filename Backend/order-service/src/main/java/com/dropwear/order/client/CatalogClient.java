package com.dropwear.order.client;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** S1 — order-service -> catalog-service : prix, nom et fournisseur faisant foi d'un SKU. */
@FeignClient(name = "catalog-service", path = "/internal/catalog")
public interface CatalogClient {

    @GetMapping("/variants/{sku}")
    VariantInfo getVariant(@PathVariable("sku") String sku);

    record VariantInfo(String sku, String productId, String productName, String size, String fit, String color,
                       BigDecimal unitPrice, String currency, UUID supplierId, boolean active) {
    }
}
