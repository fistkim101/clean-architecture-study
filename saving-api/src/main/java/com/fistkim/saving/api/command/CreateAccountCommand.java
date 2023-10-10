package com.fistkim.saving.api.command;

import com.fistkim.saving.type.AccountType;

public record CreateAccountCommand(
        Long ownerId,
        AccountType accountType,
        int initialMoney
) {
}
