package com.revolut.challenge.services.impl;

import com.revolut.challenge.cache.AccountsCache;
import com.revolut.challenge.domain.Account;
import com.revolut.challenge.domain.Balance;
import com.revolut.challenge.domain.Deposit;
import com.revolut.challenge.domain.Transfer;
import com.revolut.challenge.domain.Withdraw;
import com.revolut.challenge.services.AccountsService;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService
{
    private final AccountsCache accounts;


    /**
     * This transfer is done in two atomic and thread-safe steps:
     * 1. debit from the origin account
     * 2. credit in the destination account
     * <p>
     * I could wrap both calls in a synchronized block but this would decrease the scalability.
     * So by doing in two atomic calls we get an eventual consistency since there could be a delay between
     * the debit and credit operations.
     */
    public void transfer(Transfer transferData)
    {
        accounts.upsert(transferData.getFrom(), transferData.getAmount().negate(), false);
        accounts.upsert(transferData.getTo(), transferData.getAmount(), false);
    }


    @Override
    public BigDecimal withdraw(Withdraw data)
    {
        return accounts.upsert(data.getFrom(), data.getAmount().negate());
    }


    @Override
    public void deposit(Deposit depositData)
    {
        accounts.upsert(depositData.getTo(), depositData.getAmount(), true);
    }


    @Override
    public Optional<Account> balance(Balance data)
    {
        return accounts.get(data.getAccountId());
    }

}
