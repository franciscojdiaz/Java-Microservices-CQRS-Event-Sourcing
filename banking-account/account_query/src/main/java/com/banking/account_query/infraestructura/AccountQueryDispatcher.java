package com.banking.account_query.infraestructura;

import com.banking.cqrs_core.domain.BaseEntity;
import com.banking.cqrs_core.infrastructure.QueryDispatcher;
import com.banking.cqrs_core.queries.BaseQuery;
import com.banking.cqrs_core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {


    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        // se tiene qye registrar los handlers dentro de las rutas
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);


    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {

        var handlers = routes.get(query.getClass());
        if(handlers == null || handlers.size() <= 0){
            throw new RuntimeException("Ningun query handler fu registrado");
        }

        if(handlers.size() > 1){
            throw new RuntimeException("No puede enviar un query que tenga 2 0 mas handlers");
        }

        return handlers.get(0).handle(query);
    }
}
