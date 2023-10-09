package com.fistkim.saving.application.port.in.command;

import com.fistkim.saving.domain.Money;

public interface CloseAccountUseCase {
    Money closeAccount(Long accountId);
}
