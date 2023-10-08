package com.fistkim.bank.application.port.in.command;

import com.fistkim.saving.domain.Money;

public interface CloseAccountUseCase {
    Money closeAccount(Long accountId);
}
