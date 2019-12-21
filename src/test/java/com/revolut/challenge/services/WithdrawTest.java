package com.revolut.challenge.services;

import com.revolut.challenge.cache.AccountsCache;
import com.revolut.challenge.domain.Withdraw;
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

public class WithdrawTest
{

    private final AccountsCache accounts = Mockito.mock(AccountsCache.class);
    AccountsService service = new AccountsServiceImpl(accounts);


    @Test
    public void withdrawOk()
    {
        service.withdraw(
            Withdraw.builder().build().builder().build().builder()
                .amount(BigDecimal.TEN)
                .from(1)
                .build());

        verify(accounts, times(1))
            .upsert(eq(1), eq(BigDecimal.valueOf(-10)));
    }


    @Test
    public void withdrawExceptionally()
    {
        when(accounts.upsert(eq(1), eq(BigDecimal.valueOf(-10))))
            .thenThrow(new BadRequestException());

        Assertions
            .assertThatExceptionOfType(BadRequestException.class)
            .isThrownBy(() ->
                service.withdraw(
                    Withdraw.builder().build().builder().build().builder()
                        .amount(BigDecimal.TEN)
                        .from(1)
                        .build())
            );
    }
}
