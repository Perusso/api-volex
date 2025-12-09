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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardEntity getCard() {
        return card;
    }

    public void setCard(CardEntity card) {
        this.card = card;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
