package com.banking.cqrs_core.domain;

import com.banking.cqrs_core.events.BaseEvent;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {

    protected String id;
    private int version = -1;

    // aqui se almacena todos los cambios que fueron hecho en el aggregate en forma de eventos
    private final List<BaseEvent> changes = new ArrayList<>();
    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public String getId(){
        return this.id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    //para obtener los cambios no registrados
    public List<BaseEvent> getUncommitedChanges() {
        return this.changes;
    }

    //limpia la lista
    public void markChangesCommited(){
        this.changes.clear();
    }

    //este metodo se llamara cuando se quiera ejecutar un nuevo evento
    // o cuando quiera reprocesar un evento existente
    protected void applyChange(BaseEvent event, Boolean isNewEvent){

        try {
            // utilizamos reflexion ya que trabajamos con clase generica
            var method = getClass().getDeclaredMethod("apply",event.getClass());
            method.setAccessible(true);
            method.invoke(this, event); // ejecucion del metodo
        }catch(NoSuchMethodException e){
            logger.log(Level.WARNING, MessageFormat.format("El metodo apply no fue encontrado para {0}", event.getClass().getName()));
        }catch(Exception e){
            logger.log(Level.SEVERE, "error aplicando el evento al  aggregate", e);
        }finally {

            if(isNewEvent){
                changes.add(event);
            }
        }

    }

    // cuando quiera ejecutar un nuevo evento dentro el aggreagte

    public void raiseEvent(BaseEvent event){
        applyChange(event,true);
    }

    // para reprocesar eventos exsitente

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event -> applyChange(event, false));
    }






}
