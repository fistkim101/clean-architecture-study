package com.fistkim.saving.domain;

import java.util.Objects;

public class Money {
    private final int value;

    public static Money of(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("금액은 0원 이상이어야 합니다.");
        }
        return new Money(value);
    }

    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Money add(Money money) {
        return new Money(this.value + money.value);
    }

    public Money subtract(Money money) {
        return new Money(this.value - money.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
