package com.fistkim.saving.application.service;

import com.fistkim.saving.application.port.in.query.GetAccountBalanceUseCase;
import com.fistkim.saving.application.port.out.query.LoadAccountPort;
import com.fistkim.saving.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceUseCase {

    private final LoadAccountPort loadAccountPort;

    @Override
    public int getAccountBalance(Long accountId) {
        final Account account = this.loadAccountPort.loadAccount(accountId);
        return account.getBalance().currentBalance();
    }
}
