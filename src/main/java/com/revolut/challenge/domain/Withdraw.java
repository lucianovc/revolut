package com.revolut.challenge.domain;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Withdraw
{
    @NotNull
    Integer from;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal amount;
}
