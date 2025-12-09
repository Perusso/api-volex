package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardAlreadyBlockedException extends BusinessException {
    public CardAlreadyBlockedException(Long cardId) {
        super("Cartão " + cardId + " já está bloqueado", HttpStatus.BAD_REQUEST);
    }
}
