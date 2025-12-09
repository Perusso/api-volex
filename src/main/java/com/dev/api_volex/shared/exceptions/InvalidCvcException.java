package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCvcException extends BusinessException {
    public InvalidCvcException() {
        super("CVC inválido", HttpStatus.BAD_REQUEST);
    }
}
