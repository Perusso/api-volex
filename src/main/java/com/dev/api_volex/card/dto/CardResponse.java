package com.dev.api_volex.card.dto;

public class CardResponse {

    private Long id;
    private String cardNumber;
    private String cardholderName;
    private String expirationDate;
    private String type;
    private boolean isBlocked;

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final CardResponse response = new CardResponse();

        public Builder id(Long id) {
            response.id = id;
            return this;
        }

        public Builder cardNumber(String cardNumber) {
            response.cardNumber = cardNumber;
            return this;
        }

        public Builder cardholderName(String cardholderName) {
            response.cardholderName = cardholderName;
            return this;
        }

        public Builder expirationDate(String expirationDate) {
            response.expirationDate = expirationDate;
            return this;
        }

        public Builder type(String type) {
            response.type = type;
            return this;
        }


        public Builder isBlocked(boolean isBlocked) {
            response.isBlocked = isBlocked;
            return this;
        }

        public CardResponse build() {
            return response;
        }
    }

    // Getters
    public Long getId() { return id; }
    public String getCardNumber() { return cardNumber; }
    public String getCardholderName() { return cardholderName; }
    public String getExpirationDate() { return expirationDate; }
    public String getType() { return type; }
    public boolean isBlocked() { return isBlocked; }
}
