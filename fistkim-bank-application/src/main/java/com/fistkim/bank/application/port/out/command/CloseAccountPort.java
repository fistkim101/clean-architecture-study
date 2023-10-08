package com.fistkim.bank.application.port.out.command;

import com.fistkim.saving.domain.Money;

public interface CloseAccountPort {

    Money closeAccount(Long accountId);

}
