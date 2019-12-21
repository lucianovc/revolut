package com.revolut.challenge.controllers;

import com.revolut.challenge.domain.Withdraw;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.revolut.challenge.services.AccountsService;

@Slf4j
@Path("/withdraw")
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Builder
public class WithdrawController
{
    final AccountsService accountsService;


    @POST
    public BigDecimal execute(@NotNull @Valid Withdraw data)
    {
        log.info("withdraw: {}", data);
        return accountsService.withdraw(data);
    }
}
