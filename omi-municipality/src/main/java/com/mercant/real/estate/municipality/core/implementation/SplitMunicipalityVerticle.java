package com.mercant.real.estate.municipality.core.implementation;

import com.mercant.real.estate.municipality.core.contract.MunicipalityCore;
import com.mercant.real.estate.municipality.core.implementation.common.Common;
import com.mercant.real.estate.municipality.utils.Logger;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.ext.web.client.WebClient;
import org.hibernate.reactive.mutiny.Mutiny;

import static com.mercant.real.estate.municipality.utils.Constant.MUNICIPALITY_CHANNEL;

/**
 * The {@code SplitMunicipalityVerticle} class extends the {@code Common} class
 * and implements the {@code MunicipalityCore} interface. This verticle is designed
 * to handle tasks related to splitting municipality data and interacting with
 * external services via HTTP requests.
 *
 * <p>
 * It initializes a WebClient during its asynchronous startup phase, allowing
 * for efficient communication with external APIs or services.
 * </p>
 *
 * <p>
 * This class also serves to publish messages to the Event Bus, triggering
 * further processing of municipality data.
 * </p>
 *
 * @see Common
 * @see MunicipalityCore
 * @see io.vertx.mutiny.core.eventbus.EventBus
 * @see io.vertx.mutiny.ext.web.client.WebClient
 */
public class SplitMunicipalityVerticle extends Common implements MunicipalityCore {

    /**
     * The Hibernate Reactive SessionFactory used to manage entity persistence
     * and transactions. This field is private and initialized as needed.
     */
    private Mutiny.SessionFactory emf;  // (1)

    /**
     * The WebClient instance used for making HTTP requests to external services.
     * This field is private and initialized during the startup process.
     */
    private WebClient webClient;

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
        eventBus = vertx.eventBus();
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

    /**
     * Processes municipality tasks by publishing a "start" message to the
     * Event Bus on the {@code MUNICIPALITY_CHANNEL}. This action triggers
     * any consumers that are listening for municipality-related events.
     *
     * <p>
     * The "start" message signals the beginning of a process, allowing
     * other components to respond accordingly.
     * </p>
     *
     * @see io.vertx.mutiny.core.eventbus.Message
     */
    @Override
    public void processMunicipality() {
        eventBus.publish(MUNICIPALITY_CHANNEL, "start");
    }
}
