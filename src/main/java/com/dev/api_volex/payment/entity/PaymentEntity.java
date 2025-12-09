package com.dev.api_volex.payment.entity;

import com.dev.api_volex.business.entity.BusinessEntity;
import com.dev.api_volex.card.entity.CardEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private BusinessEntity business;

    @Column(nullable = false)
    private Long amount;  // Em centavos (R$100,00 = 10000)

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt = null;




}
