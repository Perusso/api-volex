package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardNotFoundException extends BusinessException {
    public CardNotFoundException(Long cardId) {
        super("Cartão não encontrado com ID: " + cardId, HttpStatus.NOT_FOUND);
    }
}
