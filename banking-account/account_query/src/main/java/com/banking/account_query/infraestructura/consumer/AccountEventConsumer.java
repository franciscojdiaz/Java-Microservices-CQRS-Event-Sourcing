package com.banking.account_query.infraestructura.consumer;

import com.banking.account_common.events.AccountClosedEvent;
import com.banking.account_common.events.AccountOpenedEvent;
import com.banking.account_common.events.FundsDepositedEvent;
import com.banking.account_common.events.FundsWhithdrawEvent;
import com.banking.account_query.infraestructura.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;


@Service
public class AccountEventConsumer implements EventConsumer{


    @Autowired
    private EventHandler eventHandler;

    /*cuando se quiera consumir un mensaje, un evento dentro del kafka necesitare crear un objeto de tipo listener, este va a estar
    escuchando el topic que yo decida y va a indicarle si llego un mensaje, si llega el mensaje automaticamente va a dispara la logica del
    metodo pero debo indicar que topic es el que debe revisar este listener y el groupid que pertenece este topic.
    y el groupid lo obtengo desde la configuracion, en la carpeta resource, application.yml
    group-id: bankaccConsumer
    */

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountOpenedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsDepositedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundsWhithdrawEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(FundsWhithdrawEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(AccountClosedEvent event, Acknowledgment ack) {
        eventHandler.on(event);
        ack.acknowledge();
    }
}
