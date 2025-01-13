package com.mercant.real.estate.municipality.configuration;

import com.mercant.real.estate.municipality.configuration.local.CustomSession;
import com.mercant.real.estate.municipality.configuration.local.CustomSessionFactory;
import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

public final class FileDatabaseVerticle extends DatabaseVerticle {

    /**
     * The Hibernate Reactive SessionFactory used to manage entity persistence
     * and transactions. This field is private and initialized as needed.
     */
    private Mutiny.SessionFactory emf;  // (1)


    @Override
    public Uni<Void> asyncStart() {
        Uni<Void> startHibernate = Uni.createFrom().deferred(() -> {
            emf = new CustomSessionFactory(new CustomSession());
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

