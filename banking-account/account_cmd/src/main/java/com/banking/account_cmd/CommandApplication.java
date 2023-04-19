package com.banking.account_cmd;

import com.banking.account_cmd.api.command.*;
import com.banking.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommandApplication {


    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private CommandHandler commandHandler;


    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }

    @PostConstruct
    public void registerHandler(){
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(WithdrawFunsCommand.class, commandHandler::handle);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
    }

}
