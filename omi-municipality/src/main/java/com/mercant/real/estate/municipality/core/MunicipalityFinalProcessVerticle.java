package com.mercant.real.estate.municipality.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.entity.Municipality;
import com.mercant.real.estate.municipality.model.LatitudeLongitudeAltitude;
import com.mercant.real.estate.municipality.model.NewAndOldMunicipality;
import com.mercant.real.estate.municipality.repository.implementation.MunicipalityDatabaseRepository;
import com.mercant.real.estate.municipality.repository.implementation.OldMunicipalityDatabaseRepository;
import com.mercant.real.estate.municipality.utils.Logger;
import com.mercant.real.estate.municipality.utils.UtilConverter;
import com.mercant.real.estate.municipality.webinformation.GoogleApi;
import io.smallrye.mutiny.Uni;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mercant.real.estate.core.util.SafeExecutor.exec;
import static com.mercant.real.estate.municipality.utils.Constant.ITALY_REGION;
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
    private final GoogleApi googleApi;

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
     * @param googleApi                         the googleApi instance for making HTTP requests to retrieve latitude longitude and altitude.
     * @param municipalityDatabaseRepository    the repository for accessing current municipality data.
     * @param oldMunicipalityDatabaseRepository the repository for accessing legacy municipality data.
     */
    public MunicipalityFinalProcessVerticle(EventBusVerticle eventBusVerticle,
                                            GoogleApi googleApi,
                                            MunicipalityDatabaseRepository municipalityDatabaseRepository,
                                            OldMunicipalityDatabaseRepository oldMunicipalityDatabaseRepository) {
        this.eventBusVerticle = eventBusVerticle;
        this.googleApi = googleApi;
        this.municipalityDatabaseRepository = municipalityDatabaseRepository;
        this.municipalityMap = municipalityDatabaseRepository.findAllMulti()
                .map(municipalities -> municipalities.stream()
                        .collect(Collectors.toMap(Municipality::getMunicipalityCode, Function.identity(), (first, second) -> first)))
                .await()
                .indefinitely();
        this.oldMunicipalityDatabaseRepository = oldMunicipalityDatabaseRepository;
    }

    /**
     * Converts a JSON string representation of a message into a {@link NewAndOldMunicipality} object.
     *
     * <p>This method utilizes the {@code UtilConverter.getObjectMapper()} to parse the provided JSON string
     * and map it to an instance of the {@link NewAndOldMunicipality} class. If the input JSON string is not
     * properly formatted or does not match the expected structure of {@link NewAndOldMunicipality}, an exception
     * is thrown.
     *
     * @param msg the JSON string to be converted
     * @return an instance of {@link NewAndOldMunicipality} derived from the input JSON string
     * @throws JsonProcessingException if there is an error while parsing the JSON string
     */
    private static NewAndOldMunicipality convertToSpecificClass(String msg) throws JsonProcessingException {
        return UtilConverter.getObjectMapper().readValue(msg, NewAndOldMunicipality.class);
    }

    /**
     * Updates the geocoding information (latitude, longitude, and altitude) of a {@link Municipality} object.
     *
     * <p>This method extracts latitude, longitude, and altitude from the given {@link LatitudeLongitudeAltitude} object
     * and sets these values in the provided {@link Municipality} object. It then returns the updated municipality.
     *
     * @param municipality              the {@link Municipality} object to be updated
     * @param latitudeLongitudeAltitude the geocoding data containing latitude, longitude, and altitude
     * @return the updated {@link Municipality} object with geocoding values populated
     */
    private static Municipality populateGeocodingValue(Municipality municipality, LatitudeLongitudeAltitude latitudeLongitudeAltitude) {
        municipality.setLongitude(latitudeLongitudeAltitude.longitude());
        municipality.setLatitude(latitudeLongitudeAltitude.latitude());
        municipality.setAltitude(latitudeLongitudeAltitude.altitude());
        return municipality;
    }

    /**
     * Populates geocoding information for a set of {@link Municipality} objects using an external API.
     *
     * <p>This method takes a set of {@link Municipality} objects and retrieves their geocoding data
     * (latitude, longitude, and altitude) from a Google API. The retrieved geocoding data is then
     * used to update each municipality in the set. The process is asynchronous and returns a {@link Uni}
     * representing the set of updated municipalities.
     *
     * <p>The method maps each municipality to a {@link Uni} that fetches and applies the geocoding values.
     * It then combines all the {@link Uni} objects into a single {@link Uni} that resolves when all the updates
     * are completed.
     *
     * @param municipalities the set of {@link Municipality} objects to be updated
     * @return a {@link Uni} containing a set of updated {@link Municipality} objects with populated geocoding values
     */
    private Uni<Set<Municipality>> populateGeocodingValue(Set<Municipality> municipalities) {
        Set<Uni<Municipality>> uniSet = municipalities.stream()
                .map(municipality -> googleApi.getLatitudeLongitudeAndAltitude(
                                municipality.getMunicipalityName(),
                                Integer.parseInt(municipality.getMunicipalityCode()),
                                ITALY_REGION.text())
                        .map(latitudeLongitudeAltitude -> populateGeocodingValue(municipality, latitudeLongitudeAltitude)))
                .collect(Collectors.toSet());
        return Uni.combine().all().unis(uniSet)
                .with(list -> list.stream()
                        .map(Municipality.class::cast)
                        .collect(Collectors.toSet()));
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
        eventBusVerticle.getEventBus().consumer(MUNICIPALITY_CHANNEL.text(), message ->
                exec(() -> {
                    NewAndOldMunicipality newAndOldMunicipality = convertToSpecificClass(message.body().toString());
                    populateGeocodingValue(newAndOldMunicipality.municipalityModel())
                            .subscribe()
                            .with(finalMunicipalities -> Uni.combine().all()
                                    .unis(oldMunicipalityDatabaseRepository.saveAll(newAndOldMunicipality.oldMunicipalities()),
                                            municipalityDatabaseRepository.saveAll(finalMunicipalities))
                                    .asTuple()
                                    .subscribe()
                                    .with(ignored -> Logger.info("entity save")));
                })  // Additional processing logic can be added here
        );
    }
}