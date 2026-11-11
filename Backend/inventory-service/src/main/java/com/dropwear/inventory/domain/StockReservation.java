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

/** Verrouillage temporaire : ACTIVE -> CONFIRMEE (E2) | LIBEREE (E3, DELETE) | EXPIREE (job planifié). */
@Entity
@Table(name = "stock_reservations", indexes = {@Index(columnList = "expires_at")})
@Getter
@Setter
@NoArgsConstructor
public class StockReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ReservationType referenceType;

    private String referenceId;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ReservationStatus status;

    private Instant expiresAt;

    private Instant createdAt;

    private Instant confirmedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "stock_reservation_id")
    private List<ReservationLine> lines = new ArrayList<>();
}
