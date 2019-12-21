package com.revolut.challenge.controllers;

import com.revolut.challenge.domain.Account;
import com.revolut.challenge.domain.Balance;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.revolut.challenge.services.AccountsService;

@Slf4j
@Path("/balance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Builder
public class BalanceController
{
    final AccountsService accountsService;


    @POST
    public Account execute(@NotNull @Valid Balance data)
    {
        log.info("balance: {}", data);
        return
            accountsService.balance(data)
                .orElseThrow(() -> new BadRequestException("Account does not exist."));
    }
}
