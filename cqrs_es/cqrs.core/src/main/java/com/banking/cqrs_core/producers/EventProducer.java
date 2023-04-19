package com.banking.cqrs_core.producers;

import com.banking.cqrs_core.events.BaseEvent;

public interface EventProducer {

    //metodo para producir un mensaje
    // necesito para el canal o el topic por donde viajara y el event
    void producer(String topic, BaseEvent event);
}
