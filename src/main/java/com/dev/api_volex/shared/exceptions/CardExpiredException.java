package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardExpiredException extends BusinessException {
    public CardExpiredException(Long cardId) {
        super("Cartão " + cardId + " está expirado e não pode ser ativado", HttpStatus.BAD_REQUEST);
    }
}
