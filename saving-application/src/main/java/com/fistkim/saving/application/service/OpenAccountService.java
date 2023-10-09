package com.fistkim.saving.application.service;

import com.fistkim.saving.application.port.in.command.OpenAccountUseCase;
import com.fistkim.saving.application.port.out.command.OpenAccountPort;
import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Money;
import com.fistkim.saving.type.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAccountService implements OpenAccountUseCase {

    private final OpenAccountPort openAccountPort;

    @Override
    public Account openAccount(Long ownerId, AccountType accountType, Money initialMoney) {
        final Account account = Account.open(ownerId, accountType, initialMoney);
        return this.openAccountPort.openAccount(account);
    }

}
