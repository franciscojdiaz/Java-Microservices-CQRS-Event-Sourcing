package com.banking.cqrs_core.commands;

//esta interfaz debe respetar una regla importante: debe definir la firma de un solo método abstracto
@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    void handler(T command);
}
