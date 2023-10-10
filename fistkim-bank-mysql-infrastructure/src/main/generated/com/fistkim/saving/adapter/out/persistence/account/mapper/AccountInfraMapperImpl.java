package com.fistkim.saving.adapter.out.persistence.account.mapper;

import com.fistkim.saving.adapter.out.persistence.account.AccountJpaEntity;
import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Balance;
import com.fistkim.saving.domain.Money;
import com.fistkim.saving.type.AccountType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-10T11:52:19+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.3 (Amazon.com Inc.)"
)
@Component
public class AccountInfraMapperImpl implements AccountInfraMapper {

    @Override
    public AccountJpaEntity toJpaEntity(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountJpaEntity accountJpaEntity = new AccountJpaEntity();

        accountJpaEntity.setBalance( accountBalanceMoneyValue( account ) );
        accountJpaEntity.setId( account.getId() );
        accountJpaEntity.setOwnerId( account.getOwnerId() );
        accountJpaEntity.setAccountType( account.getAccountType() );

        return accountJpaEntity;
    }

    @Override
    public Account toDomainEntity(AccountJpaEntity accountJpaEntity) {
        if ( accountJpaEntity == null ) {
            return null;
        }

        Balance balance = null;
        Long id = null;
        Long ownerId = null;
        AccountType accountType = null;

        balance = accountJpaEntityToBalance( accountJpaEntity );
        id = accountJpaEntity.getId();
        ownerId = accountJpaEntity.getOwnerId();
        accountType = accountJpaEntity.getAccountType();

        Account account = new Account( id, ownerId, accountType, balance );

        return account;
    }

    private int accountBalanceMoneyValue(Account account) {
        if ( account == null ) {
            return 0;
        }
        Balance balance = account.getBalance();
        if ( balance == null ) {
            return 0;
        }
        Money money = balance.getMoney();
        if ( money == null ) {
            return 0;
        }
        int value = money.getValue();
        return value;
    }

    protected Money accountJpaEntityToMoney(AccountJpaEntity accountJpaEntity) {
        if ( accountJpaEntity == null ) {
            return null;
        }

        int value = 0;

        value = accountJpaEntity.getBalance();

        Money money = new Money( value );

        return money;
    }

    protected Balance accountJpaEntityToBalance(AccountJpaEntity accountJpaEntity) {
        if ( accountJpaEntity == null ) {
            return null;
        }

        Money money = null;

        money = accountJpaEntityToMoney( accountJpaEntity );

        Balance balance = new Balance( money );

        return balance;
    }
}
