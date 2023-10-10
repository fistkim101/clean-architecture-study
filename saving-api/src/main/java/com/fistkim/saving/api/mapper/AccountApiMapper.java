package com.fistkim.saving.api.mapper;

import com.fistkim.saving.api.response.AccountResponse;
import com.fistkim.saving.domain.Account;

public class AccountApiMapper {

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(account.getId(), account.getOwnerId(), account.getAccountType(), account.getBalance());
    }

}

//package com.fistkim.saving.api.mapper;
//
//import com.fistkim.saving.api.response.AccountResponse;
//import com.fistkim.saving.domain.Account;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface AccountApiMapper {
//
//    // TODO : 이게 무슨 역할 하는지 도큐먼트 찾아보기
//    AccountApiMapper INSTANCE = Mappers.getMapper(AccountApiMapper.class);
//
//    AccountResponse toResponse(Account account);
//
//}
