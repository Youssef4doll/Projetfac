package com.dropwear.fulfillment.logistics.domain;

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
import com.dropwear.fulfillment.billing.domain.*;
import com.dropwear.fulfillment.domain.*;

/** Une expédition par fournisseur (dropshipping). Module logistics : aucune relation JPA avec billing (seul orderId est partagé). */
@Entity
@Table(name = "shipments", indexes = {@Index(columnList = "order_id"), @Index(columnList = "supplier_id")})
@Getter
@Setter
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;

    private String orderNumber;

    private String customerId;

    private UUID supplierId;

    private UUID rmaId;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ShipmentType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ShipmentStatus status;

    private String carrier;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    private String labelUrl;

    @Embedded
    private ShippingAddress shippingAddress;

    private LocalDate estimatedDeliveryDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shipment_id")
    private List<ShipmentItem> items = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shipment_id")
    private List<TrackingEvent> trackingEvents = new ArrayList<>();

    private Instant shippedAt;

    private Instant deliveredAt;

    private Instant createdAt;
}
