package com.banking.account_query.infraestructura.consumer;

import com.banking.account_common.events.AccountClosedEvent;
import com.banking.account_common.events.AccountOpenedEvent;
import com.banking.account_common.events.FundsDepositedEvent;
import com.banking.account_common.events.FundsWhithdrawEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    // el Payload o mensaje que llega es de tipo AccountOpenedEvent
    // una vez que se cosuma el mensaje hay que marcarlo como conocido para que otro consumer no lo vuelva a procesar
    // y generar duplicado esto lo marcamos como conocido con Acknowledgment
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWhithdrawEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
