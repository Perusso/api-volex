package com.dev.api_volex.business.entity;

import com.dev.api_volex.business.enumeration.BusinessType;
import com.dev.api_volex.payment.entity.PaymentEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "businesses")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BusinessType type;

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt = null;

    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
    private List<PaymentEntity> payments;
}
