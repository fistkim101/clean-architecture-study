package com.fistkim.saving.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @DisplayName("금액 더하기 검증")
    @Test
    void add() {
        final Money ten = Money.of(10);
        final Money twenty = Money.of(20);
        Assertions.assertThat(ten.add(twenty)).isEqualTo(Money.of(30));
    }

    @DisplayName("금액 빼기 검증")
    @Test
    void subtractPositive() {
        final Money twenty = Money.of(20);
        final Money ten = Money.of(10);
        Assertions.assertThat(twenty.subtract(ten)).isEqualTo(Money.of(10));
    }

    @DisplayName("금액 빼기 검증(마이너스)")
    @Test
    void subtractNegative() {
        final Money ten = Money.of(10);
        final Money twenty = Money.of(20);
        Assertions.assertThat(ten.subtract(twenty).getValue()).isEqualTo(-10);
    }

}