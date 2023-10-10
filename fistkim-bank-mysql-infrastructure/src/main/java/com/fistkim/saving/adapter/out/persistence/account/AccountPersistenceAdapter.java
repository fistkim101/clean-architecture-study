package com.fistkim.saving.adapter.out.persistence.account;

import com.fistkim.saving.adapter.out.persistence.account.mapper.AccountInfraMapper;
import com.fistkim.saving.application.port.out.command.CloseAccountPort;
import com.fistkim.saving.application.port.out.command.OpenAccountPort;
import com.fistkim.saving.application.port.out.command.UpdateAccountBalancePort;
import com.fistkim.saving.application.port.out.query.LoadAccountPort;
import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Balance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements CloseAccountPort, OpenAccountPort, UpdateAccountBalancePort, LoadAccountPort {

    private final AccountJpaRepository accountJpaRepository;

    private final AccountInfraMapper accountInfraMapper;

    @Override
    public void closeAccount(Long accountId) {
        final AccountJpaEntity accountJpaEntity = this.accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        this.accountJpaRepository.delete(accountJpaEntity);
    }

    @Override
    public Account openAccount(Account account) {
        final AccountJpaEntity accountJpaEntity = this.accountInfraMapper.toJpaEntity(account);
        this.accountJpaRepository.save(accountJpaEntity);
        return this.accountInfraMapper.toDomainEntity(accountJpaEntity);
    }

    @Override
    public void updateAccountBalance(Long accountId, Balance newBalance) {
        final AccountJpaEntity accountJpaEntity = this.accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        accountJpaEntity.changeBalance(newBalance.currentBalance());
        this.accountJpaRepository.save(accountJpaEntity);
    }

    @Override
    public Account loadAccount(Long accountId) {
        final AccountJpaEntity accountJpaEntity = this.accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return this.accountInfraMapper.toDomainEntity(accountJpaEntity);
    }
}
