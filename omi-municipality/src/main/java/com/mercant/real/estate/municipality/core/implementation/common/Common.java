package com.mercant.real.estate.municipality.core.implementation.common;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.mutiny.core.eventbus.EventBus;

/**
 * The {@code Common} class extends the {@code AbstractVerticle} class,
 * providing a base implementation for Vert.x verticles.
 * It serves as a common foundation for creating various verticles
 * that need access to the Vert.x Event Bus.
 *
 * <p>
 * This class is designed to be extended by other classes that will
 * implement specific functionality. It manages the Event Bus instance
 * used for inter-verticle communication.
 * </p>
 *
 * <p>
 * This class overrides the {@code asyncStart} method to ensure that
 * the superclass implementation is called. This allows for any
 * necessary setup or initialization defined in the parent class.
 * </p>
 *
 * <p>
 * Note: This class is intended to be used within the Vert.x framework
 * and should be deployed as part of a Vert.x application.
 * </p>
 *
 * @see io.vertx.core.AbstractVerticle
 * @see io.vertx.core.eventbus.EventBus
 */
public class Common extends AbstractVerticle {

    /**
     * The Event Bus instance used for sending and receiving messages
     * between verticles. This field is protected, allowing subclasses
     * to access the Event Bus directly.
     */
    protected EventBus eventBus;

    /**
     * Asynchronously starts the verticle. This method overrides the
     * {@code asyncStart} method from the {@code AbstractVerticle} class
     * to ensure that the base implementation is called.
     *
     * <p>
     * The Event Bus will be available for use after this method completes,
     * allowing the verticle to start processing messages.
     * </p>
     *
     * @return a {@link Uni} that represents the asynchronous operation.
     * @throws Exception if the start process encounters an error.
     */
    @Override
    public Uni<Void> asyncStart() {
        return super.asyncStart();
    }
}
