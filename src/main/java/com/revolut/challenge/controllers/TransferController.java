package com.revolut.challenge.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@Builder
public class TransferController
{
    private final AccountsService accountsService;


    @POST
    public void execute(@NotNull @Valid com.revolut.challenge.domain.Transfer data)
    {
        log.info("transfer: {}", data);
        accountsService.transfer(data);
    }
}
