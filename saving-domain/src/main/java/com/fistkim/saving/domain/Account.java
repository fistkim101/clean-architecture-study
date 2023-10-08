package com.fistkim.saving.domain;

import com.fistkim.saving.type.AccountType;

public class Account {

    private Long id;
    private final AccountType accountType;
    private final Balance balance;

    public static Account open(AccountType accountType, Money initialMoney) {
        final Balance balance = Balance.of(initialMoney);
        return new Account(accountType, balance);
    }

    private Account(AccountType accountType, Balance balance) {
        this.accountType = accountType;
        this.balance = balance;
    }

    public Account(Long id, AccountType accountType, Balance balance) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Balance getBalance() {
        return balance;
    }
}