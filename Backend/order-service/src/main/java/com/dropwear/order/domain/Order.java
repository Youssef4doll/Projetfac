package com.dropwear.order.domain;

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

/** Cycle de vie : EN_ATTENTE -> CONFIRMEE -> EXPEDIEE -> LIVREE ; EN_ATTENTE|CONFIRMEE -> ANNULEE. Prix et adresse figés (snapshot). */
@Entity
@Table(name = "orders", indexes = {@Index(columnList = "customer_id")})
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private PaymentMethod paymentMethod;

    @Column(precision = 12, scale = 3)
    private BigDecimal subtotal;

    @Column(precision = 12, scale = 3)
    private BigDecimal shippingFee;

    @Column(precision = 12, scale = 3)
    private BigDecimal totalAmount;

    private String currency;

    private UUID reservationId;

    @Embedded
    private ShippingAddress shippingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderLine> lines = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderStatusHistory> history = new ArrayList<>();

    private Instant createdAt;

    private Instant confirmedAt;

    private Instant shippedAt;

    private Instant deliveredAt;

    private Instant cancelledAt;

    @Version
    private long version;
}
