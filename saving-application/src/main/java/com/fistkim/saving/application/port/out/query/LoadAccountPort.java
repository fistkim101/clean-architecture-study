package com.fistkim.saving.application.port.out.query;

import com.fistkim.saving.domain.Account;

public interface LoadAccountPort {

    Account loadAccount(Long accountId);

}
