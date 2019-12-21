package com.revolut.challenge.domain;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Builder
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class Account
{
    @NotEmpty
    Integer id;

    @NotEmpty
    BigDecimal balance;
}
