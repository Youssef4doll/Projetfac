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

@Entity
@Table(name = "return_labels")
@Getter
@Setter
@NoArgsConstructor
public class ReturnLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String carrier;

    private String trackingNumber;

    private String labelUrl;

    private Instant issuedAt;

    private Instant expiresAt;
}
