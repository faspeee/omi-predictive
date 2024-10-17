package com.mercant.real.estate.municipality.core.implementation;

import com.mercant.real.estate.municipality.core.contract.MunicipalityCore;
import com.mercant.real.estate.municipality.core.implementation.common.Common;
import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.Map;

import static com.mercant.real.estate.municipality.utils.Constant.MUNICIPALITY_CHANNEL;

/**
 * The {@code MunicipalityProcessVerticle} class extends the {@code Common} class
 * and implements the {@code MunicipalityCore} interface. This class is responsible
 * for managing the processing of municipality-related tasks within a Vert.x application.
 *
 * <p>
 * This verticle establishes a connection to a PostgreSQL database using Hibernate
 * Reactive and listens for messages on a specific Event Bus channel for processing
 * municipality data.
 * </p>
 *
 * <p>
 * It initializes the Hibernate SessionFactory during its asynchronous startup phase
 * and sets up a consumer for processing municipality messages.
 * </p>
 *
 * @see Common
 * @see MunicipalityCore
 * @see io.vertx.mutiny.core.eventbus.EventBus
 */
public class MunicipalityProcessVerticle extends Common implements MunicipalityCore {

    /**
     * The Hibernate Reactive SessionFactory used to manage entity persistence
     * and transactions. This field is private and initialized during the
     * asynchronous startup process.
     */
    private Mutiny.SessionFactory emf;  // (1)

    /**
     * Asynchronously starts the verticle. This method initializes the Event Bus
     * and creates the Hibernate SessionFactory needed for database operations.
     * It also logs a message when Hibernate is ready to use.
     *
     * <p>
     * The method performs the following operations:
     * <ul>
     *     <li>Calls the superclass's {@code asyncStart} method to ensure base
     *     initialization is complete.</li>
     *     <li>Retrieves the PostgreSQL port from the configuration, defaulting
     *     to 5432 if not specified.</li>
     *     <li>Creates properties for connecting to the PostgreSQL database,
     *     including the JDBC URL.</li>
     *     <li>Creates the SessionFactory using Hibernate Reactive.</li>
     *     <li>Executes a blocking operation to ensure the initialization is
     *     completed before proceeding.</li>
     * </ul>
     * </p>
     *
     * @return a {@link Uni} that represents the asynchronous startup operation.
     * @throws Exception if an error occurs during the startup process.
     */
    @Override
    public Uni<Void> asyncStart() {
        super.asyncStart();
        eventBus = vertx.eventBus();
        Uni<Void> startHibernate = Uni.createFrom().deferred(() -> {
            var pgPort = config().getInteger("pgPort", 5432);
            var props = Map.of("jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:" + pgPort + "/postgres");  // (1)
            emf = Persistence
                    .createEntityManagerFactory("pg-demo", props)
                    .unwrap(Mutiny.SessionFactory.class);
            return Uni.createFrom().voidItem();
        });

        return vertx.executeBlocking(startHibernate)  // (2)
                .onItem().invoke(() -> Logger.info("✅ Hibernate Reactive is ready"));
    }

    /**
     * Processes municipality messages received from the Event Bus. This method sets
     * up a consumer that listens on the {@code MUNICIPALITY_CHANNEL} and logs the
     * content of each message received.
     *
     * <p>
     * The consumer is invoked whenever a message is published to the specified
     * channel, allowing for real-time processing of municipality data.
     * </p>
     *
     * @see io.vertx.mutiny.core.eventbus.Message
     */
    @Override
    public void processMunicipality() {
        eventBus.consumer(MUNICIPALITY_CHANNEL, message -> {
            Logger.info(message.body().toString());
        });
    }
}