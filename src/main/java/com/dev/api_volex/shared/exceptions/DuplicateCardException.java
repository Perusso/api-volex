package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateCardException extends BusinessException {
    public DuplicateCardException(Long employeeId, String cardType) {
        super("Empregado " + employeeId + " já possui um cartão do tipo: " + cardType, HttpStatus.CONFLICT);
    }
}
