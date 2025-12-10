package com.dev.api_volex.recharge.dto;


public class CreateRechargeRequest {

    private Long cardId;
    private Long amount; // Em centavos


    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
