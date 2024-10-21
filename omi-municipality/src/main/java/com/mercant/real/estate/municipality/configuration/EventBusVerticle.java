package com.mercant.real.estate.municipality.configuration;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.core.eventbus.EventBus;

public final class EventBusVerticle extends AbstractVerticle {
    /**
     * The Event Bus instance used for sending and receiving messages
     * between verticles. This field is protected, allowing subclasses
     * to access the Event Bus directly.
     */
    private EventBus eventBus;
 
    @Override
    public Uni<Void> asyncStart() {
        super.asyncStart();
        eventBus = vertx.eventBus();
        return Uni.createFrom().voidItem();
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
