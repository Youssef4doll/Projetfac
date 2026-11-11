package com.dropwear.fulfillment.billing.domain;

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
import com.dropwear.fulfillment.domain.*;
import com.dropwear.fulfillment.logistics.domain.*;

@Entity
@Table(name = "invoice_lines")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sku;

    private String description;

    private int quantity;

    @Column(precision = 12, scale = 3)
    private BigDecimal unitPrice;

    @Column(precision = 12, scale = 3)
    private BigDecimal lineTotal;
}
