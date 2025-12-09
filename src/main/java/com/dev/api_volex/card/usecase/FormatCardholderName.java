package com.dev.api_volex.card.usecase;


import org.springframework.stereotype.Component;

@Component
public class FormatCardholderName {

    public String format(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Nome completo não pode ser vazio");
        }

        // Remover múltiplos espaços
        fullName = fullName.trim().replaceAll("\\s+", " ");
        String[] names = fullName.split(" ");

        if (names.length == 0) {
            return "";
        }

        // Primeiro nome em maiúsculas
        String firstName = names[0].toUpperCase();

        // Iniciais dos nomes do meio (com 3+ letras)
        StringBuilder middleInitials = new StringBuilder();
        for (int i = 1; i < names.length - 1; i++) {
            if (names[i].length() >= 3) {
                middleInitials.append(" ")
                        .append(names[i].charAt(0))
                        .append(".");
            }
        }

        // Último nome em maiúsculas
        String lastName = names.length > 1
                ? names[names.length - 1].toUpperCase()
                : "";

        // Construir resultado
        StringBuilder result = new StringBuilder(firstName);
        if (middleInitials.length() > 0) {
            result.append(middleInitials);
        }
        if (!lastName.isEmpty()) {
            result.append(" ").append(lastName);
        }

        return result.toString().trim();
    }


}
