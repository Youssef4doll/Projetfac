package com.dropwear.returns.domain;

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

/** Colonne item_condition : CONDITION est un mot réservé MySQL. */
@Entity
@Table(name = "return_items")
@Getter
@Setter
@NoArgsConstructor
public class ReturnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sku;

    private String productName;

    private int quantity;

    @Column(precision = 12, scale = 3)
    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_condition", length = 40)
    private ItemCondition condition;

    private String replacementSku;

    private UUID replacementReservationId;
}
