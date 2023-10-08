package com.fistkim.saving.domain;

import com.fistkim.saving.type.AccountType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTest {

    @DisplayName("계좌 개설 검증")
    @Test
    void open() {
        final Money ten = Money.of(10);
        final Account account = Account.open(AccountType.NORMAL, ten);

        Assertions.assertThat(account.getId()).isNull();
        Assertions.assertThat(account.getAccountType()).isEqualTo(AccountType.NORMAL);
        Assertions.assertThat(account.getBalance().currentBalance()).isEqualTo(10);
    }

    @DisplayName("입금 검증")
    @Test
    void deposit() {
        final Money ten = Money.of(10);
        final Account account = Account.open(AccountType.NORMAL, ten);

        account.deposit(ten);
        Assertions.assertThat(account.getBalance().currentBalance()).isEqualTo(20);
    }

    @DisplayName("통장 출금 검증")
    @Test
    void withdraw() {
        final Money ten = Money.of(10);
        final Account account = Account.open(AccountType.NORMAL, ten);
        final Money five = Money.of(5);

        final Money money = account.withdraw(five);
        Assertions.assertThat(money.getValue()).isEqualTo(5);
    }

    @DisplayName("마이너스 통장 출금 검증")
    @Test
    void withdrawException() {
        final Money ten = Money.of(10);
        final Account account = Account.open(AccountType.MINUS, ten);
        final Money twenty = Money.of(20);

        final Money withdraw = account.withdraw(twenty);
        Assertions.assertThat(withdraw.getValue()).isEqualTo(20);
        Assertions.assertThat(account.getBalance().currentBalance()).isEqualTo(ten.subtract(twenty).getValue());
    }

    @DisplayName("계좌 해지 검증")
    @Test
    void close() {
        final Money ten = Money.of(10);
        final Account account = Account.open(AccountType.NORMAL, ten);
        final Money total = account.close();

        Assertions.assertThat(total.getValue()).isEqualTo(10);
    }

    @DisplayName("마이너스 잔액시 계좌 해지 예외처리 검증")
    @Test
    void closeException() {
        final Money ten = Money.of(10);
        final Account account = Account.open(AccountType.MINUS, ten);
        account.withdraw(Money.of(20));

        Assertions.assertThatThrownBy(account::close)
                .isInstanceOf(IllegalArgumentException.class);
    }

}