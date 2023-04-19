package com.banking.cqrs_core.events;

import com.banking.cqrs_core.messages.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
// SuperBuilder, me siver para generar una construccion fluida de objetos en las clases concretas que de genere 0 extienda del baseevent
@SuperBuilder
public abstract class BaseEvent extends Message {
    private int version;
}
