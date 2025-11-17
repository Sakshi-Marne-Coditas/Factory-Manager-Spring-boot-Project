package com.FactoryManager.Constatnts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryOf {
    PRODUCT("Product"),
    TOOL("Tool");

    private final String value;

    CategoryOf(String value) {
        this.value = value;
    }

    // Used when sending response to frontend
    @JsonValue
    public String getValue() {
        return value;
    }

    // Used when receiving value from frontend
    @JsonCreator
    public static CategoryOf fromValue(String value) {
        for (CategoryOf categoryOf : values()) {
            if (categoryOf.getValue().equalsIgnoreCase(value)) {
                return categoryOf;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + value);
    }
}
