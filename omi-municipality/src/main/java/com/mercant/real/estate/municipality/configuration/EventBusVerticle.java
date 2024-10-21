package com.mercant.real.estate.municipality.configuration;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.core.eventbus.EventBus;

/**
 * EventBusVerticle is a Vert.x verticle responsible for managing
 * the EventBus instance for inter-verticle communication.
 *
 * <p>This class initializes the EventBus during the asynchronous
 * start process, allowing other components of the application to
 * send and receive messages through it.</p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-10-21
 */
public final class EventBusVerticle extends AbstractVerticle {

    /**
     * The EventBus instance used for communication between verticles.
     * This field is private and initialized in the asyncStart method.
     */
    private EventBus eventBus;

    /**
     * Asynchronously starts the verticle and initializes the EventBus.
     *
     * <p>This method retrieves the EventBus from the Vert.x instance.
     * It completes immediately with a void item after initialization.</p>
     *
     * @return a Uni that completes when the EventBus is initialized.
     */
    @Override
    public Uni<Void> asyncStart() {
        super.asyncStart();
        eventBus = vertx.eventBus();
        return Uni.createFrom().voidItem();
    }

    /**
     * Retrieves the EventBus instance.
     *
     * <p>This method provides access to the EventBus, allowing other
     * verticles to send and receive messages.</p>
     *
     * @return the initialized EventBus instance.
     */
    public EventBus getEventBus() {
        return eventBus;
    }
}

