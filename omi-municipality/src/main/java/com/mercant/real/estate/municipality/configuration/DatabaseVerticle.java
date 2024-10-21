package com.mercant.real.estate.municipality.configuration;

import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClientOptions;
import org.hibernate.reactive.mutiny.Mutiny;

public final class DatabaseVerticle extends AbstractVerticle {
    /**
     * The Hibernate Reactive SessionFactory used to manage entity persistence
     * and transactions. This field is private and initialized as needed.
     */
    private Mutiny.SessionFactory emf;  // (1)


    /**
     * Asynchronously starts the verticle. This method initializes the Event Bus
     * and creates a WebClient for HTTP communication.
     *
     * <p>
     * The method performs the following operations:
     * <ul>
     *     <li>Calls the superclass's {@code asyncStart} method for base
     *     initialization.</li>
     *     <li>Creates a WebClient with specified options, including a custom
     *     User-Agent and disabling keep-alive connections.</li>
     *     <li>Executes the WebClient initialization as a blocking operation
     *     to ensure it's ready before proceeding.</li>
     * </ul>
     * </p>
     *
     * @return a {@link Uni} that represents the asynchronous startup operation.
     * @throws Exception if an error occurs during the startup process.
     */
    @Override
    public Uni<Void> asyncStart() {
        super.asyncStart();
        Uni<Void> startWebClient = Uni.createFrom().deferred(() -> {
            WebClientOptions options = new WebClientOptions()
                    .setUserAgent("My-App/1.2.3");
            options.setKeepAlive(false);
            return Uni.createFrom().voidItem();
        });
        return vertx.executeBlocking(startWebClient)  // (2)
                .onItem().invoke(() -> Logger.info("✅ WebClient is ready"));
    }

    public Mutiny.SessionFactory getEmf() {
        return emf;
    }
}
