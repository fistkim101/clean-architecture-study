package com.fistkim.saving.adapter.out.persistence.account.mapper;

import com.fistkim.saving.adapter.out.persistence.account.AccountJpaEntity;
import com.fistkim.saving.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountInfraMapper {
    AccountInfraMapper INSTANCE = Mappers.getMapper(AccountInfraMapper.class);

    @Mapping(source = "balance.money.value", target = "balance")
    AccountJpaEntity toJpaEntity(Account account);

    @Mapping(source = "balance", target = "balance.money.value")
    Account toDomainEntity(AccountJpaEntity accountJpaEntity);
}
