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

@Entity
@Table(name = "restock_alerts")
@Getter
@Setter
@NoArgsConstructor
public class RestockAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sku;

    private UUID supplierId;

    private int availableAtAlert;

    private int threshold;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private AlertStatus status;

    private Instant createdAt;

    private Instant resolvedAt;
}
