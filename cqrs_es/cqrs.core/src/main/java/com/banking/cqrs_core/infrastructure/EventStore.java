package com.banking.cqrs_core.infrastructure;

import com.banking.cqrs_core.events.BaseEvent;

import java.util.List;

public interface EventStore {

    void saveEvents(String aggreagteId, Iterable<BaseEvent> events, int expectedVersion);

    List<BaseEvent> getEvents(String aggreagteId);

}
