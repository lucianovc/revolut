package com.revolut.challenge.config;

import io.dropwizard.Configuration;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AppConfiguration extends Configuration
{
    @NotEmpty
    private String appName;
}
