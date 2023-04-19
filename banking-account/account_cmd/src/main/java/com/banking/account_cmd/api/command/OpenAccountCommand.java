package com.banking.account_cmd.api.command;

import com.banking.account_common.dto.AccountType;
import com.banking.cqrs_core.commands.BaseCommand;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class OpenAccountCommand extends BaseCommand {
    // nombre de la persona a la que se le crea la cuenta
    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
