package com.dropwear.inventory.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** @Version : verrou optimiste, une seule réservation gagne sur la dernière pièce. */
@Entity
@Table(name = "stock_items", indexes = {@Index(columnList = "supplier_id")})
@org.hibernate.annotations.Check(constraints = "quantity_reserved <= quantity_on_hand")
@Getter
@Setter
@NoArgsConstructor
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String sku;

    private String productId;

    private UUID supplierId;

    private int quantityOnHand;

    private int quantityReserved;

    private int reorderThreshold;

    private Instant updatedAt;

    @Version
    private long version;
    /** Disponible à la vente = physique - réservé. */
    public int available() {
        return quantityOnHand - quantityReserved;
    }
}
