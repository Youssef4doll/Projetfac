package com.dropwear.inventory.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dropwear.inventory.domain.*;

public interface StockItemRepository extends JpaRepository<StockItem, UUID> {

    Optional<StockItem> findBySku(String sku);

    List<StockItem> findBySupplierId(UUID supplierId);

    List<StockItem> findBySkuIn(Collection<String> skus);
}
