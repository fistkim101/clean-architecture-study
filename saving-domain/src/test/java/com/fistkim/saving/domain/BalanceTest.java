package com.fistkim.saving.domain;

import com.fistkim.saving.type.AccountType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BalanceTest {

    @DisplayName("현재 잔액 검증")
    @Test
    void currentBalance() {
        final Money ten = Money.of(10);

        final Balance balance = Balance.of(ten);

        Assertions.assertThat(balance.currentBalance()).isEqualTo(10);
    }

    @DisplayName("입금시 잔액 검증")
    @Test
    void deposit() {
        final Money ten = Money.of(10);
        final Balance balance = Balance.of(ten);

        final Money twenty = Money.of(20);
        balance.deposit(twenty);

        Assertions.assertThat(balance.currentBalance()).isEqualTo(30);
    }

    @DisplayName("일반 통장 출금시 잔액 검증")
    @Test
    void withdrawNormalAccount() {
        final Money ten = Money.of(10);
        final Balance balance = Balance.of(ten);

        final Money twenty = Money.of(20);
        Assertions.assertThatThrownBy(() -> balance.withdraw(AccountType.NORMAL, twenty))
                .isInstanceOf(IllegalArgumentException.class);

        final Integer WITHDRAW_MONEY = 5;
        final Money five = Money.of(WITHDRAW_MONEY);
        final Money withdraw = balance.withdraw(AccountType.NORMAL, five);
        Assertions.assertThat(withdraw.getValue()).isEqualTo(WITHDRAW_MONEY);
        Assertions.assertThat(balance.currentBalance()).isEqualTo(ten.subtract(five).getValue());
    }

    @DisplayName("마이너스 통장 출금시 잔액 검증")
    @Test
    void withdrawMinusAccount() {
        final Money ten = Money.of(10);
        final Balance balance = Balance.of(ten);

        final Money twenty = Money.of(20);
        final Money withdraw = balance.withdraw(AccountType.MINUS, twenty);
        Assertions.assertThat(withdraw.getValue()).isEqualTo(twenty.getValue());
        Assertions.assertThat(balance.currentBalance()).isEqualTo(ten.subtract(twenty).getValue());
    }
}