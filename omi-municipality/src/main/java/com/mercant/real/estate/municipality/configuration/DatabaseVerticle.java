package com.mercant.real.estate.municipality.configuration;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import org.hibernate.reactive.mutiny.Mutiny;

public abstract class DatabaseVerticle extends AbstractVerticle {

    /**
     * Asynchronously starts the verticle and initializes the Hibernate
     * Reactive SessionFactory.
     *
     * <p>This method retrieves the PostgreSQL port from the configuration,
     * constructs the JDBC URL, and creates the SessionFactory. It logs
     * a message indicating that Hibernate Reactive is ready after successful
     * initialization.</p>
     *
     * @return a Uni that completes when the SessionFactory is initialized.
     */
    @Override
    public abstract Uni<Void> asyncStart();

    /**
     * Retrieves the Hibernate Reactive SessionFactory.
     *
     * <p>This method provides access to the SessionFactory, allowing
     * for the creation of sessions for database operations.</p>
     *
     * @return the initialized SessionFactory.
     */
    public abstract Mutiny.SessionFactory getEmf();
}
