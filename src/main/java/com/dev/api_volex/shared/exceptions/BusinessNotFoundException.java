package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessNotFoundException extends BusinessException {
    public BusinessNotFoundException(Long businessId) {
        super("Estabelecimento não encontrado com ID: " + businessId, HttpStatus.NOT_FOUND);
    }
}
