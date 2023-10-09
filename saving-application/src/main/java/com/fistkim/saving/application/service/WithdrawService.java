package com.fistkim.saving.application.service;

import com.fistkim.saving.application.port.in.command.WithdrawUseCase;
import com.fistkim.saving.application.port.out.command.UpdateAccountBalancePort;
import com.fistkim.saving.application.port.out.query.LoadAccountPort;
import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawService implements WithdrawUseCase {

    private final LoadAccountPort loadAccountPort;

    private final UpdateAccountBalancePort updateAccountBalancePort;

    @Override
    public Money withdraw(Long accountId, Money money) {
        final Account account = this.loadAccountPort.loadAccount(accountId);
        final Money withdrawnAmount = account.withdraw(money);
        this.updateAccountBalancePort.updateAccountBalance(accountId, account.getBalance());

        return withdrawnAmount;
    }

}
