package com.dev.api_volex.card.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TransactionResponse {

    private Long id;
    private Long cardId;
    private Long businessId;
    private String businessName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime timestamp;

    private Long amount; // Em centavos

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final TransactionResponse response = new TransactionResponse();

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder cardId(Long cardId) {
            response.cardId = cardId;
            return this;
        }

        public Builder businessId(Long businessId) {
            response.businessId = businessId;
            return this;
        }

        public Builder businessName(String businessName) {
            response.businessName = businessName;
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

        public TransactionResponse build() {
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

    public Long getBusinessId() {
        return businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getAmount() {
        return amount;
    }
}
