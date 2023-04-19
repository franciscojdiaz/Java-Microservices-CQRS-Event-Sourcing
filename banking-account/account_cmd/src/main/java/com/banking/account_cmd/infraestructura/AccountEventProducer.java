package com.banking.account_cmd.infraestructura;

import com.banking.cqrs_core.events.BaseEvent;
import com.banking.cqrs_core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {

    // para enviar el mensaje necesito un objeto de tipo kafka template
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void producer(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}
