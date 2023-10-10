package com.fistkim.saving.api.adapter.web;

import com.fistkim.saving.api.command.CreateAccountCommand;
import com.fistkim.saving.api.mapper.AccountApiMapper;
import com.fistkim.saving.api.response.AccountResponse;
import com.fistkim.saving.application.port.in.command.CloseAccountUseCase;
import com.fistkim.saving.application.port.in.command.OpenAccountUseCase;
import com.fistkim.saving.application.port.in.command.WithdrawUseCase;
import com.fistkim.saving.application.port.in.query.GetAccountBalanceUseCase;
import com.fistkim.saving.domain.Account;
import com.fistkim.saving.domain.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SavingController {

    private final OpenAccountUseCase accountUseCase;
    private final GetAccountBalanceUseCase getAccountBalanceUseCase;

    private final WithdrawUseCase withdrawUseCase;

    private final CloseAccountUseCase closeAccountUseCase;

    @PostMapping("/account")
    public ResponseEntity<AccountResponse> openAccount(@RequestBody CreateAccountCommand command) {
        final Account account = this.accountUseCase.openAccount(command.ownerId(), command.accountType(), Money.of(command.initialMoney()));
        final AccountResponse accountResponse = AccountApiMapper.toResponse(account);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<Integer> getAccountBalance(@PathVariable(value = "accountId") Long accountId) {
        final int currentBalance = this.getAccountBalanceUseCase.getAccountBalance(accountId);
        return ResponseEntity.ok(currentBalance);
    }

    @PutMapping("/account/{accountId}/withdraw/{money}")
    public ResponseEntity<Integer> withdraw(@PathVariable(value = "accountId") Long accountId, @PathVariable(value = "money") int money) {
        final Money withdrawnMoney = this.withdrawUseCase.withdraw(accountId, Money.of(money));
        return ResponseEntity.ok(withdrawnMoney.getValue());
    }

    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<Integer> closeAccount(@PathVariable(value = "accountId") Long accountId) {
        final Money money = this.closeAccountUseCase.closeAccount(accountId);
        return ResponseEntity.ok(money.getValue());
    }

}
