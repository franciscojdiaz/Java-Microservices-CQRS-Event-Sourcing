package com.banking.account_cmd.infraestructura;

import com.banking.account_cmd.domain.AccountAggregate;
import com.banking.cqrs_core.domain.AggregateRoot;
import com.banking.cqrs_core.handlers.EventSourcingHandlers;
import com.banking.cqrs_core.infrastructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;


//como es una clase tipo implementacion la anotamos con service
@Service
public class AccountEventSourcingHandler implements EventSourcingHandlers<AccountAggregate> {

    // debo inyectar el event store para poder llevar a cabo las transacciones

    @Autowired
    private EventStore eventStore;


    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(), aggregate.getVersion());
        aggregate.markChangesCommited();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);

        if(events != null && !events.isEmpty()){
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }
}
