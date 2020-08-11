package com.mateuszjanczak.springjwtboilerplate.entity;

public enum RoleName {

    role_user("user"),
    role_admin("admin");

    public final String label;

    RoleName(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

}