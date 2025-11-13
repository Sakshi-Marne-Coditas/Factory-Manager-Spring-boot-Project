package com.FactoryManager.Constatnts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ToolCondition {
    RETURNED("Returned"),
    BROKEN("Broken"),
    PENDING("Pending");

    private final String value;

    ToolCondition(String value) {
        this.value = value;
    }

    // Used when sending response to frontend
    @JsonValue
    public String getValue() {
        return value;
    }
    // Used when receiving value from frontend
    @JsonCreator
    public static ToolCondition fromValue(String value) {
        for (ToolCondition condition : values()) {
            if (condition.getValue().equalsIgnoreCase(value)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Unknown tool condition: " + value);
    }
}