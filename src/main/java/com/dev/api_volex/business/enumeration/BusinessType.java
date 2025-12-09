package com.dev.api_volex.business.enumeration;

public enum BusinessType {
    EDUCATION("education"),
    GROCERIES("groceries"),
    RESTAURANT("restaurant"),
    TRANSPORT("transport"),
    HEALTH("health");

    private final String value;

    BusinessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BusinessType fromString(String text) {
        for (BusinessType type : BusinessType.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de negócio inválido: " + text);
    }
}
