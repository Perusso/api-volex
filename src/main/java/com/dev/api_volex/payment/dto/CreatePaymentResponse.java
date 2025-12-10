package com.dev.api_volex.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CreatePaymentResponse {

    private Long id;
    private Long cardId;
    private Long businessId;
    private String businessName;
    private Long amount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime timestamp;

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final CreatePaymentResponse response = new CreatePaymentResponse();

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

        public Builder amount(Long amount) {
            response.amount = amount;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            response.timestamp = timestamp;
            return this;
        }

        public CreatePaymentResponse build() {
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

    public Long getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
