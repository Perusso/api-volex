package com.dev.api_volex.card.enumeration;

public enum CardType {

    GROCERIES("groceries"),
    RESTAURANTS("restaurants"),
    TRANSPORT("transport"),
    EDUCATION("education"),
    HEALTH("health");

    private final String value;

    CardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CardType fromString(String text) {
        for (CardType type : CardType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de cartão inválido: " + text);
    }
}
