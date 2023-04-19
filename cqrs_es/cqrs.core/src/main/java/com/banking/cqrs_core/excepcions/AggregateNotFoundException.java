package com.banking.cqrs_core.excepcions;

public class AggregateNotFoundException extends RuntimeException{


    public AggregateNotFoundException(String message) {
        super(message);
    }
}
