package com.FactoryManager.Constatnts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    WORKER("Worker"),
    CHIEF_SUPERVISOR("Chief-Supervisor"),
    PLANT_HEAD("Plant-Head"),
    OWNER("Owner"),
    CENTRAL_OFFICER("Central-Officer"),
    DISTRIBUTOR("Distributor"),
    CUSTOMER("Customer");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    // Used when sending response to frontend
    @JsonValue
    public String getValue() {
        return value;
    }

    // Used when receiving value from frontend
    @JsonCreator
    public static Role fromValue(String value) {
        for (Role role : values()) {
            if (role.getValue().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
