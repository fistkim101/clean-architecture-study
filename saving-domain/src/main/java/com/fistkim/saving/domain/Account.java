package com.fistkim.saving.domain;

import com.fistkim.saving.type.AccountType;
import lombok.Getter;

@Getter
public class Account {

    private Long id;
    private final Long ownerId;
    private final AccountType accountType;
    private final Balance balance;

    public static Account open(Long ownerId, AccountType accountType, Money initialMoney) {
        final Balance balance = Balance.of(initialMoney);
        return new Account(ownerId, accountType, balance);
    }

    private Account(Long ownerId, AccountType accountType, Balance balance) {
        this.ownerId = ownerId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Account(Long id, Long ownerId, AccountType accountType, Balance balance) {
        this.id = id;
        this.ownerId = ownerId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public void deposit(Money amount) {
        this.balance.deposit(amount);
    }

    public Money withdraw(Money amount) {
        return this.balance.withdraw(this.accountType, amount);
    }

    public Money close() {
        int currentBalance = this.balance.currentBalance();
        if (currentBalance < 0) {
            throw new IllegalArgumentException("계좌 해지를 위해 채무 상환이 필요합니다. [상환 금액: " + currentBalance + "]");
        }

        return Money.of(currentBalance);
    }

}
