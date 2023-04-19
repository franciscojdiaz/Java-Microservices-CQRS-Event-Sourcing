package com.banking.account_cmd.infraestructura;

import com.banking.cqrs_core.commands.BaseCommand;
import com.banking.cqrs_core.commands.CommandHandlerMethod;
import com.banking.cqrs_core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


// para que esta clase sea inyectable le agregamos la anotacion service
@Service
public class AccountCommandDispatcher implements CommandDispatcher {
                     // cualquier clase que extienda
                     // basecommand
    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {
        // un command solo puede tener un handler
        var handlers = routes.get(command.getClass());

        if(handlers == null || handlers.size() == 0){
            throw new RuntimeException("El command handler no fue registrado");
        }

        if(handlers.size() > 1){
            throw new RuntimeException("No se puede lanzar un command que tiene mas de un handler");
        }

        // este metodo handler es quien envia la comunicacion
        handlers.get(0).handler(command);

    }
}
