package com.example.managementweb.entities;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");
    private final String value;

    Role(String value) {
        this.value = value;
    }
}
