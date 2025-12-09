package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCardPasswordException extends BusinessException {
    public InvalidCardPasswordException() {
        super("Senha do cartão inválida", HttpStatus.BAD_REQUEST);
    }
}
