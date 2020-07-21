package com.mateuszjanczak.springjwtboilerplate.entity;

public enum RoleName {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    public final String label;

    RoleName(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

}