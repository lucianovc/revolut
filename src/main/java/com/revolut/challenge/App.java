package com.revolut.challenge;

import com.revolut.challenge.cache.AccountsCache;
import com.google.common.collect.Maps;
import com.revolut.challenge.config.AppConfiguration;
import com.revolut.challenge.controllers.BalanceController;
import com.revolut.challenge.controllers.DepositController;
import com.revolut.challenge.controllers.TransferController;
import com.revolut.challenge.controllers.WithdrawController;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import com.revolut.challenge.services.impl.AccountsServiceImpl;

public class App extends Application<AppConfiguration>
{
    public void run(AppConfiguration appConfiguration, Environment environment) throws Exception
    {
        AccountsCache accountsCache = AccountsCache.builder()
            .data(Maps.newConcurrentMap())
            .build();

        AccountsServiceImpl accountsService = AccountsServiceImpl.builder()
            .accounts(accountsCache)
            .build();
        environment.jersey()
            .register(TransferController.builder()
                .accountsService(accountsService)
                .build());

        environment.jersey()
            .register(DepositController.builder()
                .accountsService(accountsService)
                .build());

        environment.jersey()
            .register(WithdrawController.builder()
                .accountsService(accountsService)
                .build());

        environment.jersey()
            .register(BalanceController.builder()
                .accountsService(accountsService)
                .build());
    }


    public static void main(String[] args) throws Exception
    {
        new App().run(args);
    }
}
