package com.FactoryManager.Constatnts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ToolType {
    EXPENSIVE("Expensive"),
    NORMAL("Normal");

    private final String value;

    ToolType(String value) {
        this.value = value;
    }

    // Used when sending response to frontend
    @JsonValue
    public String getValue() {
        return value;
    }

    // Used when receiving value from frontend
    @JsonCreator
    public static ToolType fromValue(String value) {
        for (ToolType type : values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown tool type: " + value);
    }
}
