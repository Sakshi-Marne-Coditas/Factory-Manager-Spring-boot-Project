package com.FactoryManager.Constatnts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestStatus {
    APPROVED("Approved"),
    REJECTED("Rejected"),
    PENDING("Pending"),
    PENDING_FROM_PM("Pending from Project Manager"),
    DELIVERED("Delivered");

    private final String value;

    RequestStatus(String value) {
        this.value = value;
    }

    // Used when sending response to frontend
    @JsonValue
    public String getValue() {
        return value;
    }

    // Used when receiving value from frontend
    @JsonCreator
    public static RequestStatus fromValue(String value) {
        for (RequestStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown request status: " + value);
    }
}
