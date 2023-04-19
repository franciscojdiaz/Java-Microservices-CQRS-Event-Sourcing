package com.banking.cqrs_core.handlers;

import com.banking.cqrs_core.domain.AggregateRoot;

public interface EventSourcingHandlers<T> {

    void save(AggregateRoot aggregate);
    T getById(String id);

}
