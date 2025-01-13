package com.mercant.real.estate.municipality.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.entity.Municipality;
import com.mercant.real.estate.municipality.model.NewAndOldMunicipality;
import com.mercant.real.estate.municipality.repository.implementation.MunicipalityDatabaseRepository;
import com.mercant.real.estate.municipality.repository.implementation.OldMunicipalityDatabaseRepository;
import com.mercant.real.estate.municipality.utils.Logger;
import com.mercant.real.estate.municipality.utils.UtilConverter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mercant.real.estate.municipality.utils.Constant.MUNICIPALITY_CHANNEL;

/**
 * MunicipalityFinalProcessVerticle is responsible for processing municipality-related
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
public final class MunicipalityFinalProcessVerticle implements MunicipalityCore {

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
    private final MunicipalityDatabaseRepository municipalityDatabaseRepository;
    private final Map<String, Municipality> municipalityMap;

    /**
     * The repository for accessing old municipality data.
     */
    private final OldMunicipalityDatabaseRepository oldMunicipalityDatabaseRepository;

    /**
     * Constructs a MunicipalityFinalProcessVerticle with the specified dependencies.
     *
     * @param eventBusVerticle                  the EventBusVerticle instance for message consumption.
     * @param webClientVerticle                 the WebClientVerticle instance for making HTTP requests.
     * @param municipalityDatabaseRepository    the repository for accessing current municipality data.
     * @param oldMunicipalityDatabaseRepository the repository for accessing legacy municipality data.
     */
    public MunicipalityFinalProcessVerticle(EventBusVerticle eventBusVerticle,
                                            WebClientVerticle webClientVerticle,
                                            MunicipalityDatabaseRepository municipalityDatabaseRepository,
                                            OldMunicipalityDatabaseRepository oldMunicipalityDatabaseRepository) {
        this.eventBusVerticle = eventBusVerticle;
        this.webClientVerticle = webClientVerticle;
        this.municipalityDatabaseRepository = municipalityDatabaseRepository;
        this.municipalityMap = municipalityDatabaseRepository.findAllMulti()
                .map(municipalities -> municipalities.stream()
                        .collect(Collectors.toMap(Municipality::getMunicipalityCode, Function.identity())))
                .await()
                .indefinitely();
        this.oldMunicipalityDatabaseRepository = oldMunicipalityDatabaseRepository;
    }

    private static NewAndOldMunicipality convertToSpecificClass(String msg) throws JsonProcessingException {
        return UtilConverter.getObjectMapper().readValue(msg, NewAndOldMunicipality.class);
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
        eventBusVerticle.getEventBus().consumer(MUNICIPALITY_CHANNEL.text(), message -> {
            try {
                NewAndOldMunicipality newAndOldMunicipality = convertToSpecificClass(message.body().toString());
                municipalityDatabaseRepository.saveAll(newAndOldMunicipality.municipalityModel())
                        .subscribe()
                        .with(ignored -> Logger.info("entity save"));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            // Additional processing logic can be added here
        });
    }
}