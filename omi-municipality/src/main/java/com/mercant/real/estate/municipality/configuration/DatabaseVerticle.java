package com.mercant.real.estate.municipality.configuration;

import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.Map;

/**
 * DatabaseVerticle is a Vert.x verticle responsible for managing
 * the Hibernate Reactive SessionFactory for entity persistence
 * and database transactions.
 *
 * <p>This class initializes the Hibernate SessionFactory using the
 * configuration provided and exposes a method to retrieve the
 * SessionFactory for further database operations.</p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-10-21
 */
public final class DatabaseVerticle extends AbstractVerticle {

    /**
     * The Hibernate Reactive SessionFactory used to manage entity persistence
     * and transactions. This field is private and initialized as needed.
     */
    private Mutiny.SessionFactory emf;  // (1)

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
    public Uni<Void> asyncStart() {
        super.asyncStart();
        Uni<Void> startHibernate = Uni.createFrom().deferred(() -> {
            var pgPort = config().getInteger("pgPort", 5432);
            var props = Map.of("jakarta.persistence.jdbc.url",
                    "jdbc:postgresql://localhost:" + pgPort + "/postgres");  // (1)
            emf = Persistence
                    .createEntityManagerFactory("pg-demo", props)
                    .unwrap(Mutiny.SessionFactory.class);
            return Uni.createFrom().voidItem();
        });

        return vertx.executeBlocking(startHibernate)  // (2)
                .onItem().invoke(() -> Logger.info("✅ Hibernate Reactive is ready"));
    }

    /**
     * Retrieves the Hibernate Reactive SessionFactory.
     *
     * <p>This method provides access to the SessionFactory, allowing
     * for the creation of sessions for database operations.</p>
     *
     * @return the initialized SessionFactory.
     */
    public Mutiny.SessionFactory getEmf() {
        return emf;
    }
}
