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

/** Module billing. Numérotation séquentielle sans trou (FAC-2026-000123), AVOIR lié par relatedInvoiceId. */
@Entity
@Table(name = "invoices", indexes = {@Index(columnList = "order_id")})
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    private UUID orderId;

    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private InvoiceType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private InvoiceStatus status;

    private LocalDate issueDate;

    @Column(precision = 12, scale = 3)
    private BigDecimal subtotal;

    @Column(precision = 12, scale = 3)
    private BigDecimal taxRate;

    @Column(precision = 12, scale = 3)
    private BigDecimal taxAmount;

    @Column(precision = 12, scale = 3)
    private BigDecimal totalAmount;

    private String currency;

    private String pdfUrl;

    private UUID relatedInvoiceId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceLine> lines = new ArrayList<>();
}
