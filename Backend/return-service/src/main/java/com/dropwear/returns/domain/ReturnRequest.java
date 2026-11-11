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

/** RMA : DEMANDEE -> APPROUVEE | REFUSEE -> COLIS_RECU -> CLOTUREE. eligibilityDeadline = orderDeliveredAt + 14 jours. */
@Entity
@Table(name = "return_requests", indexes = {@Index(columnList = "order_id"), @Index(columnList = "customer_id")})
@Getter
@Setter
@NoArgsConstructor
public class ReturnRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String rmaNumber;

    private UUID orderId;

    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ReturnType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ReturnStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ReturnReason reason;

    private String comment;

    private Instant orderDeliveredAt;

    private Instant eligibilityDeadline;

    private String ticketId;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ReturnOutcome outcome;

    private String decidedBy;

    private String decisionComment;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "return_request_id")
    private List<ReturnItem> items = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "label_id")
    private ReturnLabel label;

    private Instant createdAt;

    private Instant updatedAt;
}
