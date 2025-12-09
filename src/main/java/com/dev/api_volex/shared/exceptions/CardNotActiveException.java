package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardNotActiveException extends BusinessException {
    public CardNotActiveException(Long cardId) {
        super("Cartão " + cardId + " não está ativo", HttpStatus.BAD_REQUEST);
    }
}
