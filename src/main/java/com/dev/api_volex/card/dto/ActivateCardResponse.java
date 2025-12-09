package com.dev.api_volex.card.dto;

public class ActivateCardResponse {

    private Long cardId;
    private String message;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ActivateCardResponse response = new ActivateCardResponse();

        public Builder cardId(Long cardId) {
            response.cardId = cardId;
            return this;
        }

        public Builder message(String message) {
            response.message = message;
            return this;
        }

        public ActivateCardResponse build() {
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
}
