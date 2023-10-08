package com.fistkim.bank.application.port.out.command;

import com.fistkim.saving.domain.Account;

public interface OpenAccountPort {

    Account openAccount(Account account);

}
