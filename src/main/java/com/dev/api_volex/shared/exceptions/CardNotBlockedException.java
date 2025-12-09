package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardNotBlockedException extends BusinessException {
    public CardNotBlockedException(Long cardId) {
        super("Cartão " + cardId + " não está bloqueado", HttpStatus.BAD_REQUEST);
    }
}
