package com.dev.api_volex.recharge.entity;

import com.dev.api_volex.card.entity.CardEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recharges")
public class RechargeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;

    @Column(nullable = false)
    private Long amount;  // Em centavos (R$100,00 = 10000)

    @Column(name = "created_at", nullable = true)
    private LocalDateTime createdAt = null;
}
