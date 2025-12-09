package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCardTypeException extends BusinessException {
    public InvalidCardTypeException(String cardType) {
        super("Tipo de cartão inválido: " + cardType + ". Tipos válidos: groceries, restaurants, transport, education, health",
                HttpStatus.BAD_REQUEST);
    }
}
