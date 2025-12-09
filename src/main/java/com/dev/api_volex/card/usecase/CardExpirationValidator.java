package com.dev.api_volex.card.usecase;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CardExpirationValidator {

    private static final DateTimeFormatter EXPIRATION_FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    public boolean isExpired(String expirationDate) {
        // Formato MM/yy
        String[] parts = expirationDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        // Adiciona 2000 ao ano (ex: 27 → 2027)
        int fullYear = 2000 + year;

        // O cartão expira no último dia do mês
        LocalDate expiration = LocalDate.of(fullYear, month, 1)
                .plusMonths(1) // Próximo mês
                .minusDays(1); // Último dia do mês atual

        LocalDate today = LocalDate.now();

        return today.isAfter(expiration);
    }

    public String getExpirationDateFormatted() {
        LocalDate now = LocalDate.now();
        LocalDate expiration = now.plusYears(5);
        return expiration.format(EXPIRATION_FORMATTER);
    }
}
