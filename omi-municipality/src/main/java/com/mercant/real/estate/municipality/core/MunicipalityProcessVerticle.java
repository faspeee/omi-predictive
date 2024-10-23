package com.mercant.real.estate.municipality.core;

import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.repository.MunicipalityRepository;
import com.mercant.real.estate.municipality.repository.OldMunicipalityRepository;
import com.mercant.real.estate.municipality.utils.Logger;

import static com.mercant.real.estate.municipality.utils.Constant.MUNICIPALITY_CHANNEL;

/**
 * MunicipalityProcessVerticle is responsible for processing municipality-related
 * messages from the EventBus. It implements the MunicipalityCore interface and
 * coordinates actions between various components such as the EventBus, WebClient,
 * and repositories.
 *
 * <p>This verticle listens for messages on a designated municipality channel
 * and performs processing tasks based on the received messages.</p>
 *
 * <p>It utilizes the {@link EventBusVerticle} for message consumption,
 * {@link WebClientVerticle} for making HTTP requests if needed, and
 * repositories for database interactions.</p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-10-21
 */
public final class MunicipalityProcessVerticle implements MunicipalityCore {

    /**
     * The EventBusVerticle instance used for receiving messages from the EventBus.
     */
    private final EventBusVerticle eventBusVerticle;

    /**
     * The WebClientVerticle instance used for making HTTP requests to external services.
     */
    private final WebClientVerticle webClientVerticle;

    /**
     * The repository for accessing municipality data.
     */
    private final MunicipalityRepository municipalityRepository;

    /**
     * The repository for accessing old municipality data.
     */
    private final OldMunicipalityRepository oldMunicipalityRepository;

    /**
     * Constructs a MunicipalityProcessVerticle with the specified dependencies.
     *
     * @param eventBusVerticle          the EventBusVerticle instance for message consumption.
     * @param webClientVerticle         the WebClientVerticle instance for making HTTP requests.
     * @param municipalityRepository    the repository for accessing current municipality data.
     * @param oldMunicipalityRepository the repository for accessing legacy municipality data.
     */
    public MunicipalityProcessVerticle(EventBusVerticle eventBusVerticle,
                                       WebClientVerticle webClientVerticle,
                                       MunicipalityRepository municipalityRepository,
                                       OldMunicipalityRepository oldMunicipalityRepository) {
        this.eventBusVerticle = eventBusVerticle;
        this.webClientVerticle = webClientVerticle;
        this.municipalityRepository = municipalityRepository;
        this.oldMunicipalityRepository = oldMunicipalityRepository;
    }

    /**
     * Processes municipality messages received from the EventBus.
     *
     * <p>This method subscribes to the MUNICIPALITY_CHANNEL on the EventBus and
     * logs the received message body. Additional processing logic can be added
     * to handle the messages appropriately, such as database interactions or
     * external service calls.</p>
     *
     * <p>Example usage:
     * <pre>
     *     processMunicipality();
     * </pre>
     * This method should be invoked to start listening for messages.</p>
     */
    @Override
    public void processMunicipality() {
        eventBusVerticle.getEventBus().consumer(MUNICIPALITY_CHANNEL, message -> {
            Logger.info(message.body().toString());
            // Additional processing logic can be added here
        });
    }
}