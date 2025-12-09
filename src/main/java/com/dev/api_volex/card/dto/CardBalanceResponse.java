package com.dev.api_volex.card.dto;

import java.util.List;

public class CardBalanceResponse {

    private Long balance;
    private List<TransactionResponse> transactions;
    private List<RechargeResponse> recharges;

    // Builder Pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final CardBalanceResponse response = new CardBalanceResponse();

        public Builder balance(Long balance) {
            response.balance = balance;
            return this;
        }

        public Builder transactions(List<TransactionResponse> transactions) {
            response.transactions = transactions;
            return this;
        }

        public Builder recharges(List<RechargeResponse> recharges) {
            response.recharges = recharges;
            return this;
        }

        public CardBalanceResponse build() {
            return response;
        }
    }

    // Getters
    public Long getBalance() {
        return balance;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public List<RechargeResponse> getRecharges() {
        return recharges;
    }
}
