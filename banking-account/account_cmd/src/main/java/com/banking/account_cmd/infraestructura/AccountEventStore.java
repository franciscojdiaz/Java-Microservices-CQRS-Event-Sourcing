package com.banking.account_cmd.infraestructura;

import com.banking.account_cmd.domain.AccountAggregate;
import com.banking.account_cmd.domain.EventStoreRepository;
import com.banking.cqrs_core.events.BaseEvent;
import com.banking.cqrs_core.events.EventModel;
import com.banking.cqrs_core.excepcions.AggregateNotFoundException;
import com.banking.cqrs_core.excepcions.ConcurrencyException;
import com.banking.cqrs_core.infrastructure.EventStore;
import com.banking.cqrs_core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountEventStore implements EventStore {


    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Autowired
    private EventProducer eventProducer;

    @Override
    public void saveEvents(String aggreagteId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggreagteId);

        if(expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion){
            throw new ConcurrencyException();
        }

        var version = expectedVersion;
        for (var event: events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggreagteId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            // si el persistedEvent es null quiere decir que no se hizo la insercion en la BD, de lo contrario
            // llamo al kafka para enviar la data al evento

            if(!persistedEvent.getId().isEmpty()){
                // producir un evento para kafka
                eventProducer.producer(event.getClass().getSimpleName(), event);
            }

        }


    }

    @Override
    public List<BaseEvent> getEvents(String aggreagteId) {

        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggreagteId);
        if(eventStream == null || eventStream.isEmpty()){
            throw new AggregateNotFoundException("La cuenta del banco es incorrecta");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }
}
