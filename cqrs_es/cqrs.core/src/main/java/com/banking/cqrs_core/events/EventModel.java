package com.banking.cqrs_core.events;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
// esta anotacion le indicara al spring que sera un documento que se almacenara en una base de datos Mongo DB
// mongo DB almacena colecciones y una coleccion esta conformada por un conjunto de documentos y la estructura
// de un documento es similiar a la de un formato Json, para mongo db es Bson.
// si fuera una BD relacional usara @Table
@Document(collection = "eventStore")
public class EventModel {

    @Id
    private String id;
    private Date timeStamp;
    private String aggregateIdentifier;
    private String aggregateType;
    private int version;
    private String eventType;
    // almacena la descripcion de  operacion que se quiere realizar
    private BaseEvent eventData;


}
