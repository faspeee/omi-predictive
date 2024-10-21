package com.mercant.real.estate.municipality.configuration;

import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.ext.web.client.WebClient;

/**
 * WebClientVerticle is a Vert.x verticle responsible for managing
 * the WebClient instance for making HTTP requests to external services.
 *
 * <p>This class initializes the WebClient during the asynchronous
 * start process, allowing the application to communicate with
 * external APIs or services efficiently.</p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-10-21
 */
public final class WebClientVerticle extends AbstractVerticle {

    /**
     * The WebClient instance used for making HTTP requests to external services.
     * This field is private and initialized during the startup process.
     */
    private WebClient webClient;

    /**
     * Asynchronously starts the verticle and initializes the WebClient.
     *
     * <p>This method creates a WebClientOptions instance to configure
     * the WebClient, including setting a user agent and disabling keep-alive.
     * The WebClient is created and ready for use after this method completes.</p>
     *
     * @return a Uni that completes when the WebClient is initialized.
     */
    @Override
    public Uni<Void> asyncStart() {
        super.asyncStart();
        Uni<Void> startWebClient = Uni.createFrom().deferred(() -> {
            WebClientOptions options = new WebClientOptions()
                    .setUserAgent("My-App/1.2.3")
                    .setKeepAlive(false);
            webClient = WebClient.create(vertx, options);
            return Uni.createFrom().voidItem();
        });

        return vertx.executeBlocking(startWebClient)  // (2)
                .onItem().invoke(() -> Logger.info("✅ WebClient is ready"));
    }

    /**
     * Retrieves the WebClient instance.
     *
     * <p>This method provides access to the WebClient, allowing the
     * application to perform HTTP requests to external services.</p>
     *
     * @return the initialized WebClient instance.
     */
    public WebClient getWebClient() {
        return webClient;
    }
}
