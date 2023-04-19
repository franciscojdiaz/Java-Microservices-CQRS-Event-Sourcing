package com.banking.cqrs_core.infrastructure;


import com.banking.cqrs_core.commands.BaseCommand;
import com.banking.cqrs_core.commands.CommandHandlerMethod;

//este va a trabajar como que fuera el mediator
public interface CommandDispatcher {

    // es una metodo generico pero que provengan del base command, registrar los handler
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    // enviar los objetos commands
    void send(BaseCommand command);


}
