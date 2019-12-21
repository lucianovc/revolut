package com.revolut.challenge.services;

import com.revolut.challenge.cache.AccountsCache;
import com.revolut.challenge.domain.Balance;
import javax.ws.rs.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.revolut.challenge.services.impl.AccountsServiceImpl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BalanceTest
{

    private final AccountsCache accounts = Mockito.mock(AccountsCache.class);
    AccountsService service = new AccountsServiceImpl(accounts);


    @Test
    public void balanceOk()
    {
        service.balance(
            Balance.builder().build().builder()
                .accountId(1)
                .build());

        verify(accounts, times(1))
            .get(eq(1));
    }


    @Test
    public void balanceExceptionally()
    {
        when(accounts.get(eq(1)))
            .thenThrow(new BadRequestException());

        Assertions
            .assertThatExceptionOfType(BadRequestException.class)
            .isThrownBy(() ->
                service.balance(
                    Balance.builder().build().builder()
                        .accountId(1)
                        .build())
            );
    }
}
