package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardAlreadyActiveException extends BusinessException {
    public CardAlreadyActiveException(Long cardId) {
        super("Cartão " + cardId + " já está ativo", HttpStatus.BAD_REQUEST);
    }
}
