package com.fistkim.saving.type;

public enum AccountType {
    NORMAL("일반 통장"),
    MINUS("마이너스 통장");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }
}
