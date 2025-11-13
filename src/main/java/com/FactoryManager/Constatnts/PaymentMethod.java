package com.FactoryManager.Constatnts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentMethod {
    DEBIT_CARD("Debit Card"),
    CREDIT_CARD("Credit Card");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    // Used when sending response to frontend
    @JsonValue
    public String getValue() {
        return value;
    }

    // Used when receiving value from frontend
    @JsonCreator
    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod method : values()) {
            if (method.getValue().equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + value);
    }
}