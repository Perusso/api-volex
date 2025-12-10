package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class IncompatibleCardTypeException extends BusinessException {
    public IncompatibleCardTypeException(Long cardId, String cardType, Long businessId, String businessType) {
        super("Cartão " + cardId + " (tipo: " + cardType + ") não é compatível com estabelecimento " + businessId + " (tipo: " + businessType + ")",
                HttpStatus.BAD_REQUEST);
    }
}
