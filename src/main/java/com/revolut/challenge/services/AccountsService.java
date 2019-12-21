package com.revolut.challenge.services;

import com.revolut.challenge.domain.Account;
import com.revolut.challenge.domain.Balance;
import java.math.BigDecimal;
import java.util.Optional;

public interface AccountsService
{
    void transfer(com.revolut.challenge.domain.Transfer transferData);

    BigDecimal withdraw(com.revolut.challenge.domain.Withdraw data);

    void deposit(com.revolut.challenge.domain.Deposit depositData);

    Optional<Account> balance(Balance data);


}
