package com.fistkim.saving.application.service;

import com.fistkim.saving.application.port.in.command.CloseAccountUseCase;
import com.fistkim.saving.application.port.out.command.CloseAccountPort;
import com.fistkim.saving.application.port.out.query.LoadAccountPort;
import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CloseAccountService implements CloseAccountUseCase {

    private final LoadAccountPort loadAccountPort;
    private final CloseAccountPort closeAccountPort;

    @Override
    public Money closeAccount(Long accountId) {
        final Account account = this.loadAccountPort.loadAccount(accountId);
        final Money money = account.close();

        this.closeAccountPort.closeAccount(accountId);
        return money;
    }
}
