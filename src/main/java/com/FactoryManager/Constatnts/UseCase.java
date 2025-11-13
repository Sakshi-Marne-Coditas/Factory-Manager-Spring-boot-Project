package com.FactoryManager.Constatnts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UseCase {
    PERISHABLE("Perishable"),
    NON_PERISHABLE("Non-Perishable");

    private final String value;

    UseCase(String value) {
        this.value = value;
    }

    // Used when sending response to frontend
    @JsonValue
    public String getValue() {
        return value;
    }

    // Used when receiving value from frontend
    @JsonCreator
    public static UseCase fromValue(String value) {
        for (UseCase useCase : values()) {
            if (useCase.getValue().equalsIgnoreCase(value)) {
                return useCase;
            }
        }
        throw new IllegalArgumentException("Unknown use case: " + value);
    }
}