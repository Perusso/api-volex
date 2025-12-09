package com.dev.api_volex.card.dto;

public class UnblockCardResponse {
    private Long cardId;
    private String message;
    private boolean isBlocked;

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final UnblockCardResponse response = new UnblockCardResponse();

        public Builder cardId(Long cardId) {
            response.cardId = cardId;
            return this;
        }

        public Builder message(String message) {
            response.message = message;
            return this;
        }

        public Builder isBlocked(boolean isBlocked) {
            response.isBlocked = isBlocked;
            return this;
        }

        public UnblockCardResponse build() {
            return response;
        }
    }

    // Getters
    public Long getCardId() {
        return cardId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
}
