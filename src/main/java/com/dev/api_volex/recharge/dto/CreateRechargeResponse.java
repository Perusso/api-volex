package com.dev.api_volex.recharge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CreateRechargeResponse {
    private Long id;
    private Long cardId;
    private Long amount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime timestamp;

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final CreateRechargeResponse response = new CreateRechargeResponse();

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder cardId(Long cardId) {
            response.cardId = cardId;
            return this;
        }

        public Builder amount(Long amount) {
            response.amount = amount;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            response.timestamp = timestamp;
            return this;
        }

        public CreateRechargeResponse build() {
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

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
