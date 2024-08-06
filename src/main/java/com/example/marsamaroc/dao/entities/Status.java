package com.example.marsamaroc.dao.entities;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    REJECTED,
    ACCEPTED,
    PENDING;

    @JsonCreator
    public static Status fromString(String key) {
        return key == null ? null : Status.valueOf(key.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}
