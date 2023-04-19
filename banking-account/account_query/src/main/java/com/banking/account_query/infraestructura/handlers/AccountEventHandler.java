package com.banking.account_query.infraestructura.handlers;

import com.banking.account_common.events.AccountClosedEvent;
import com.banking.account_common.events.AccountOpenedEvent;
import com.banking.account_common.events.FundsDepositedEvent;
import com.banking.account_common.events.FundsWhithdrawEvent;
import com.banking.account_query.domain.AccountRepository;
import com.banking.account_query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountEventHandler implements EventHandler{


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreateDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if(bankAccount.isEmpty()){
            return;
        }

        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWhithdrawEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if(bankAccount.isEmpty()){
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var latestBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
