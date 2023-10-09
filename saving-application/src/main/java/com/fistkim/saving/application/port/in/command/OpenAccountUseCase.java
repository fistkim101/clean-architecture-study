package com.fistkim.saving.application.port.in.command;

import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Money;
import com.fistkim.saving.type.AccountType;

public interface OpenAccountUseCase {
    Account openAccount(Long ownerId, AccountType accountType, Money initialMoney);
}
