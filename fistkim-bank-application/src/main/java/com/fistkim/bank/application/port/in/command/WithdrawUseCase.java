package com.fistkim.bank.application.port.in.command;

import com.fistkim.saving.domain.Money;

public interface WithdrawUseCase {
    Money withdraw(Long accountId, Money money);
}
