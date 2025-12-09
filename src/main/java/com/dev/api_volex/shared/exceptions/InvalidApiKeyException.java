package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

// Exceções específicas
public class InvalidApiKeyException extends BusinessException {
    public InvalidApiKeyException(String apiKey) {
        super("API Key inválida: " + apiKey, HttpStatus.UNAUTHORIZED);
    }
}
