package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class CardBlockedException extends BusinessException {
    public CardBlockedException(Long cardId) {
        super("Cartão " + cardId + " está bloqueado e não pode receber recargas",
                HttpStatus.BAD_REQUEST);
    }
}
