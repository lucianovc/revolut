package com.revolut.challenge.controllers;

import com.revolut.challenge.domain.Deposit;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import com.revolut.challenge.services.AccountsService;

@Slf4j
@Path("/deposit")
@Consumes(MediaType.APPLICATION_JSON)
@Builder
public class DepositController
{
    final AccountsService accountsService;


    @POST
    public void execute(@NotNull @Valid Deposit data)
    {
        log.info("deposit: {}", data);
        accountsService.deposit(data);
    }
}
