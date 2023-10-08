package com.fistkim.saving.domain;

import com.fistkim.saving.type.AccountType;

public class Balance {
    private Money money;

    public static Balance of(Money money) {
        return new Balance(money);
    }

    private Balance(Money money) {
        this.money = money;
    }

    public int currentBalance() {
        return this.money.getValue();
    }

    public void deposit(Money amount) {
        this.money = this.money.add(amount);
    }

    public Money withdraw(AccountType accountType, Money amount) {
        Money newBalance = this.money.subtract(amount);
        if (newBalance.getValue() < 0 && accountType == AccountType.NORMAL) {
            throw new IllegalArgumentException("잔고가 부족합니다.");
        }

        this.money = newBalance;
        return Money.of(amount.getValue());
    }

}