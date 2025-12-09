package com.dev.api_volex.card.usecase;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class GenerateCreditCardData {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    private final Faker faker;

    public GenerateCreditCardData() {
        this.faker = new Faker();
    }

    public String generateCreditCardNumber() {
        return faker.finance().creditCard();
    }

    public String generateCreditCardCvv() {
        return faker.number().digits(3);
    }

    public String generateExpirationDate() {
        LocalDate now = LocalDate.now();
        LocalDate expirationDate = now.plusYears(5);
        return expirationDate.format(FORMATTER);
    }
}
