package com.mercant.real.estate.municipality.configuration;

import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.ext.web.client.WebClient;

public final class WebClientVerticle extends AbstractVerticle {
    /**
     * The WebClient instance used for making HTTP requests to external services.
     * This field is private and initialized during the startup process.
     */
    private WebClient webClient;
    
    @Override
    public Uni<Void> asyncStart() {
        super.asyncStart();
        Uni<Void> startWebClient = Uni.createFrom().deferred(() -> {
            WebClientOptions options = new WebClientOptions()
                    .setUserAgent("My-App/1.2.3");
            options.setKeepAlive(false);
            webClient = WebClient.create(vertx, options);
            return Uni.createFrom().voidItem();
        });
        return vertx.executeBlocking(startWebClient)  // (2)
                .onItem().invoke(() -> Logger.info("✅ WebClient is ready"));
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
