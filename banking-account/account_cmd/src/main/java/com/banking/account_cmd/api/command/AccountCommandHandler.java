package com.banking.account_cmd.api.command;

import com.banking.account_cmd.domain.AccountAggregate;
import com.banking.cqrs_core.handlers.EventSourcingHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// como es una clase que implementa
@Service
public class AccountCommandHandler implements CommandHandler{

    @Autowired
    private EventSourcingHandlers<AccountAggregate> eventSourcingHandlers;


    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandlers.save(aggregate);
    }

    @Override
    public void handle(DepositFundsCommand command) {
        var aggregate = eventSourcingHandlers.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandlers.save(aggregate);
    }

    @Override
    public void handle(WithdrawFunsCommand command) {
        var aggregate = eventSourcingHandlers.getById(command.getId());

        if(command.getAmount() > aggregate.getBalance()){
            throw new IllegalStateException("Insuficiente fondos");
        }

        aggregate.withDrawFunds(command.getAmount());
        eventSourcingHandlers.save(aggregate);
    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourcingHandlers.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandlers.save(aggregate);

    }
}
