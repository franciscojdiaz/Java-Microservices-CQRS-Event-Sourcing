package com.banking.account_cmd.api.command;

import com.banking.cqrs_core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFunsCommand extends BaseCommand {

    private double amount;
}
