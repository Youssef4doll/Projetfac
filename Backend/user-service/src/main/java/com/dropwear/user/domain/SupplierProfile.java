package com.dropwear.user.domain;

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
@Table(name = "supplier_profiles")
@Getter
@Setter
@NoArgsConstructor
public class SupplierProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String companyName;

    @Column(unique = true, nullable = false)
    private String taxId;

    private String contactEmail;

    private String contactPhone;

    private String country;

    private int shippingDelayDays;

    private boolean active;
}
