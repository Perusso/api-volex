package com.dev.api_volex.shared.exceptions;

import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends BusinessException {
    public EmployeeNotFoundException(Long employeeId) {
        super("Empregado não encontrado com ID: " + employeeId, HttpStatus.NOT_FOUND);
    }
}
