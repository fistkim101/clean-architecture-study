package com.fistkim.saving.application.port.out.command;

import com.fistkim.saving.domain.Balance;

public interface UpdateAccountBalancePort {

    void updateAccountBalance(Long accountId, Balance newBalance);

}
