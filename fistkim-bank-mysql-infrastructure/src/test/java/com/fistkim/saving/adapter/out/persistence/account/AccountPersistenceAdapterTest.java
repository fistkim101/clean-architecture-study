package com.fistkim.saving.adapter.out.persistence.account;

import com.fistkim.saving.adapter.out.persistence.account.mapper.AccountInfraMapperImpl;
import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Money;
import com.fistkim.saving.type.AccountType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@Import({AccountInfraMapperImpl.class, AccountPersistenceAdapter.class})
@ActiveProfiles(value = "test")
class AccountPersistenceAdapterTest {

    @Autowired
    private AccountPersistenceAdapter accountPersistenceAdapter;

    @Autowired
    private AccountJpaRepository accountJpaRepository;


    @DisplayName("계좌 개설 검증")
    @Test
    void openAccount() {
        final Long OWNER_ID = 1L;
        final AccountType ACCOUNT_TYPE = AccountType.NORMAL;
        final Money INITIAL_MONEY = Money.of(1000);

        final Account account = Account.open(OWNER_ID, ACCOUNT_TYPE, INITIAL_MONEY);
        final Account openedAccount = this.accountPersistenceAdapter.openAccount(account);
        final Optional<AccountJpaEntity> openedAccountJpaEntity = this.accountJpaRepository.findById(openedAccount.getId());

        Assertions.assertThat(openedAccountJpaEntity).isNotEmpty();
        Assertions.assertThat(openedAccountJpaEntity.get().getOwnerId()).isEqualTo(OWNER_ID);
        Assertions.assertThat(openedAccountJpaEntity.get().getAccountType()).isEqualTo(ACCOUNT_TYPE);
        Assertions.assertThat(openedAccountJpaEntity.get().getBalance()).isEqualTo(INITIAL_MONEY.getValue());
    }

    @DisplayName("계좌 해지 검증")
    @Test
    void closeAccount() {
        final Long OWNER_ID = 1L;
        final AccountType ACCOUNT_TYPE = AccountType.NORMAL;
        final Money INITIAL_MONEY = Money.of(1000);
        final Account account = Account.open(OWNER_ID, ACCOUNT_TYPE, INITIAL_MONEY);
        final Account openedAccount = this.accountPersistenceAdapter.openAccount(account);
        final Optional<AccountJpaEntity> openedAccountJpaEntity = this.accountJpaRepository.findById(openedAccount.getId());
        Assertions.assertThat(openedAccountJpaEntity).isNotEmpty();

        final Long accountId = openedAccountJpaEntity.get().getId();
        this.accountPersistenceAdapter.closeAccount(accountId);
        final Optional<AccountJpaEntity> closedAccountJpaEntity = this.accountJpaRepository.findById(accountId);
        Assertions.assertThat(closedAccountJpaEntity).isEmpty();
    }

    @DisplayName("계좌 잔액 변경 검증")
    @Test
    void updateAccountBalance() {
        final Long OWNER_ID = 1L;
        final AccountType ACCOUNT_TYPE = AccountType.NORMAL;
        final Money INITIAL_MONEY = Money.of(1000);
        final Account account = Account.open(OWNER_ID, ACCOUNT_TYPE, INITIAL_MONEY);
        final Account openedAccount = this.accountPersistenceAdapter.openAccount(account);

        openedAccount.deposit(Money.of(1000));
        this.accountPersistenceAdapter.updateAccountBalance(openedAccount.getId(), openedAccount.getBalance());

        final Optional<AccountJpaEntity> openedAccountJpaEntity = this.accountJpaRepository.findById(openedAccount.getId());
        Assertions.assertThat(openedAccountJpaEntity).isNotEmpty();
        Assertions.assertThat(openedAccountJpaEntity.get().getBalance()).isEqualTo(2000);
    }

}