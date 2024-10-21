package com.mercant.real.estate.municipality.core.implementation;

import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.core.contract.MunicipalityCore;

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
 * @see MunicipalityCore
 * @see io.vertx.mutiny.core.eventbus.EventBus
 * @see io.vertx.mutiny.ext.web.client.WebClient
 */
public final class SplitMunicipalityVerticle implements MunicipalityCore {

    private final EventBusVerticle eventBusVerticle;
    private final WebClientVerticle webClientVerticle;

    public SplitMunicipalityVerticle(EventBusVerticle eventBusVerticle, WebClientVerticle webClientVerticle) {
        this.eventBusVerticle = eventBusVerticle;
        this.webClientVerticle = webClientVerticle;
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
        eventBusVerticle.getEventBus().publish(MUNICIPALITY_CHANNEL, "start");
    }
}
