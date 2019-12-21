package com.revolut.challenge.cache;

import com.revolut.challenge.domain.Account;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import lombok.Builder;
import lombok.Getter;

import static java.lang.String.format;

@Builder
public class AccountsCache
{
    @Getter
    @Builder.Default
    Map<Integer, Account> data;


    public Optional<Account> get(Integer accountId)
    {
        return Optional.ofNullable(data.getOrDefault(accountId, null));
    }


    public BigDecimal upsert(Integer accountId, final BigDecimal amount, boolean createIfNotExistent)
    {
        return data.compute(accountId, (k, v) -> {
            Account account = null;
            if (v == null)
            {
                if (!createIfNotExistent)
                {
                    throw new BadRequestException(format("AccountId is invalid: [%s]", accountId));
                }
                account = Account.builder()
                    .id(k)
                    .balance(amount)
                    .build();
            }
            else
            {
                account = v.withBalance(v.getBalance().add(amount));
            }
            if (account.getBalance().compareTo(BigDecimal.ZERO) < 0)
            {
                throw new BadRequestException(format("Negative balance not allowed. Account [%s]", account.getId()));
            }
            return account;
        }).getBalance();
    }


    public BigDecimal upsert(Integer from, BigDecimal value)
    {
        return upsert(from, value, false);
    }
}
