package com.mercant.real.estate.municipality.core.implementation;

import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.core.contract.MunicipalityCore;
import com.mercant.real.estate.municipality.utils.Logger;
import com.mercant.real.estate.municipality.webinformation.MunicipalityInformation;

/**
 * SplitMunicipalityVerticle is responsible for initiating the splitting
 * of municipality-related processes by publishing messages to the EventBus.
 *
 * <p>This verticle interacts with the {@link EventBusVerticle} for sending
 * messages that trigger processing tasks in other components of the application.
 * It serves as a command issuer, signaling the start of municipality-related operations.</p>
 *
 * <p>Utilizing the WebClient, it can also extend functionality to make HTTP
 * requests to external services if needed in the future.</p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2024-10-21
 */
public final class SplitMunicipalityVerticle implements MunicipalityCore {

    private static final String MUNICIPALITY_CHANNEL = "municipality.channel"; // Channel name for event bus messages

    /**
     * The EventBusVerticle instance used for publishing messages to the EventBus.
     */
    private final EventBusVerticle eventBusVerticle;

    private final MunicipalityInformation municipalityInformation;

    /**
     * Constructs a SplitMunicipalityVerticle with the specified dependencies.
     *
     * @param eventBusVerticle the EventBusVerticle instance for message publication.
     */
    public SplitMunicipalityVerticle(EventBusVerticle eventBusVerticle, MunicipalityInformation municipalityInformation) {
        this.eventBusVerticle = eventBusVerticle;
        this.municipalityInformation = municipalityInformation;
    }

    /**
     * Initiates the process of splitting municipalities by publishing a message
     * to the EventBus.
     *
     * <p>This method publishes a "start" message to the MUNICIPALITY_CHANNEL,
     * signaling other components to begin their processing tasks related to
     * municipalities. This acts as a command to trigger further actions.</p>
     *
     * <p>Example usage:
     * <pre>
     *     splitMunicipalityVerticle.processMunicipality();
     * </pre>
     * This method should be called when the splitting process needs to be initiated.</p>
     */
    @Override
    public void processMunicipality() {
        Logger.info("Publishing start message to the municipality channel.");
        municipalityInformation.readCurrentMunicipalities().await().indefinitely();
        eventBusVerticle.getEventBus().publish(MUNICIPALITY_CHANNEL, "start");
    }
}