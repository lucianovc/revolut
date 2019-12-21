package com.revolut.challenge.cache;

import com.revolut.challenge.domain.Account;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static java.lang.String.format;

public class AccountCacheTest
{

    AccountsCache accounts;


    @BeforeEach
    void beforeAll()
    {
        Map<Integer, Account> data = new HashMap<>();
        data.put(1, Account.builder()
            .balance(BigDecimal.TEN)
            .id(1)
            .build());

        accounts = AccountsCache.builder()
            .data(data)
            .build();
    }


    static Object[][] testBalance()
    {
        return new Object[][] {
            new Object[] {1, BigDecimal.TEN, BigDecimal.valueOf(20), null},
            new Object[] {1, BigDecimal.TEN.negate(), BigDecimal.ZERO, null},
            new Object[] {1, BigDecimal.ONE, BigDecimal.valueOf(11), null},
            new Object[] {1, BigDecimal.ONE.negate(), BigDecimal.valueOf(9), null},
            new Object[] {1, BigDecimal.valueOf(-11), null, "Negative balance not allowed. Account [%s]"},
            new Object[] {2, BigDecimal.TEN, null, "AccountId is invalid: [%s]"},
        };
    }


    @ParameterizedTest
    @MethodSource("testBalance")
    public void testUpsert(Integer accountId, BigDecimal amountToAdd, BigDecimal result, String errorMessage)
    {
        if (errorMessage != null)
        {
            Assertions
                .assertThatThrownBy(() -> accounts.upsert(accountId, amountToAdd))
                .hasMessage(format(errorMessage, accountId));
        }
        else
        {
            accounts.upsert(accountId, amountToAdd);

            Assertions
                .assertThat(accounts.get(accountId))
                .isEqualTo(
                    Optional.of(
                        Account.builder()
                            .balance(result)
                            .id(accountId)
                            .build()));
        }
    }
}
