package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException(Long cardId, Long currentBalance, Long requiredAmount) {
        super("Cartão " + cardId + " possui saldo insuficiente. Saldo atual: " + currentBalance + ", valor necessário: " + requiredAmount,
                HttpStatus.BAD_REQUEST);
    }
}
