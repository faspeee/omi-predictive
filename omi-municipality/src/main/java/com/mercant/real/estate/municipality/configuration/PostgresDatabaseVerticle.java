package com.mercant.real.estate.municipality.configuration;

import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.Map;

/**
 * PostgresDatabaseVerticle is a Vert.x verticle responsible for managing
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
public final class PostgresDatabaseVerticle extends DatabaseVerticle {

    /**
     * The Hibernate Reactive SessionFactory used to manage entity persistence
     * and transactions. This field is private and initialized as needed.
     */
    private Mutiny.SessionFactory emf;  // (1)


    @Override
    public Uni<Void> asyncStart() {
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

    @Override
    public Mutiny.SessionFactory getEmf() {
        return emf;
    }
}
