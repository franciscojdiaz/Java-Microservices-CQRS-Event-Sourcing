package com.banking.account_cmd.domain;

import com.banking.account_cmd.api.command.OpenAccountCommand;
import com.banking.account_common.events.AccountClosedEvent;
import com.banking.account_common.events.AccountOpenedEvent;
import com.banking.account_common.events.FundsDepositedEvent;
import com.banking.account_common.events.FundsWhithdrawEvent;
import com.banking.cqrs_core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Boolean active;
    private double balance;

    public double getBalance(){
        return this.balance;
    }

    // el command que sera enviado por el cliente
    public AccountAggregate(OpenAccountCommand command){

        //cuando yo dispare este evento debo llamar al raiseEvent, significa que se va acrear un nuevo vento
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createDate(new Date())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }


    public void depositFunds(double amount){

        if(!this.active){
            throw new IllegalStateException("Los fondos no pueden ser depositado en esta cuenta");
        }

        if(amount <= 0){
            throw new IllegalStateException("El deposito no puede ser menor que cero");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());

    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        this.balance += event.getAmount();

    }

    public void withDrawFunds(double amount){
        if(!this.active){
            throw new IllegalStateException("La cuenta bancaria esta cerrada");
        }

        raiseEvent(FundsWhithdrawEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWhithdrawEvent event){

        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount(){
        if(!active){
            throw new IllegalStateException("La cuenta bancaria esta cerrada");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }

}
