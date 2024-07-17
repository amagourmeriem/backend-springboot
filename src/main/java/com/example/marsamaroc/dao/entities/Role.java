package com.example.marsamaroc.dao.entities;

public enum Role {
    ASSISTANT("Assistant"),
    DEMANDEUR("Demandeur");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
