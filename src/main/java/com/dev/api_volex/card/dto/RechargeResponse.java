package com.dev.api_volex.card.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class RechargeResponse {
    private Long id;
    private Long cardId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime timestamp;

    private Long amount; // Em centavos

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final RechargeResponse response = new RechargeResponse();

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder cardId(Long cardId) {
            response.cardId = cardId;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            response.timestamp = timestamp;
            return this;
        }

        public Builder amount(Long amount) {
            response.amount = amount;
            return this;
        }

        public RechargeResponse build() {
            return response;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getCardId() {
        return cardId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getAmount() {
        return amount;
    }
}
