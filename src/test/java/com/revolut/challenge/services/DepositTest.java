package com.revolut.challenge.services;

import com.revolut.challenge.cache.AccountsCache;
import com.revolut.challenge.domain.Deposit;
import java.math.BigDecimal;
import javax.ws.rs.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.revolut.challenge.services.impl.AccountsServiceImpl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DepositTest
{

    private final AccountsCache accounts = Mockito.mock(AccountsCache.class);
    AccountsService service = new AccountsServiceImpl(accounts);


    @Test
    public void depositOk()
    {
        service.deposit(
            Deposit.builder().build().builder()
                .amount(BigDecimal.TEN)
                .to(1)
                .build());

        verify(accounts, times(1))
            .upsert(eq(1), eq(BigDecimal.valueOf(10)), eq(true));
    }


    @Test
    public void depositExceptionally()
    {
        when(accounts.upsert(eq(1), eq(BigDecimal.valueOf(10)), eq(true)))
            .thenThrow(new BadRequestException());

        Assertions
            .assertThatExceptionOfType(BadRequestException.class)
            .isThrownBy(() ->
                service.deposit(
                    Deposit.builder().build().builder()
                        .amount(BigDecimal.TEN)
                        .to(1)
                        .build())
            );
    }
}
