package com.mercant.real.estate.municipality.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.convert.MunicipalityConverter;
import com.mercant.real.estate.municipality.entity.Municipality;
import com.mercant.real.estate.municipality.entity.OldMunicipality;
import com.mercant.real.estate.municipality.model.MunicipalityModel;
import com.mercant.real.estate.municipality.model.NewAndOldMunicipality;
import com.mercant.real.estate.municipality.model.OldAndCurrentMunicipality;
import com.mercant.real.estate.municipality.model.OldMunicipalityModel;
import com.mercant.real.estate.municipality.repository.implementation.MunicipalityDatabaseRepository;
import com.mercant.real.estate.municipality.utils.Logger;
import com.mercant.real.estate.municipality.utils.UtilConverter;
import com.mercant.real.estate.municipality.webinformation.MunicipalityInformation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mercant.real.estate.core.util.SafeExecutor.exec;
import static com.mercant.real.estate.municipality.convert.MunicipalityConverter.fromMunicipality;
import static com.mercant.real.estate.municipality.convert.MunicipalityConverter.fromOldMunicipalityModel;
import static com.mercant.real.estate.municipality.utils.Constant.MUNICIPALITY_CHANNEL;

/**
 * ProcessMunicipalityVerticle is responsible for initiating the splitting
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
public final class ProcessMunicipalityVerticle implements MunicipalityCore {


    /**
     * The EventBusVerticle instance used for publishing messages to the EventBus.
     */
    private final EventBusVerticle eventBusVerticle;

    private final MunicipalityInformation municipalityInformation;

    private final Map<String, Municipality> municipalityMap;
    /**
     * The repository for accessing municipality data.
     */
    private final MunicipalityDatabaseRepository municipalityDatabaseRepository;

    /**
     * Constructs a ProcessMunicipalityVerticle with the specified dependencies.
     *
     * @param eventBusVerticle the EventBusVerticle instance for message publication.
     */
    public ProcessMunicipalityVerticle(EventBusVerticle eventBusVerticle, MunicipalityInformation municipalityInformation, MunicipalityDatabaseRepository municipalityDatabaseRepository) {
        this.eventBusVerticle = eventBusVerticle;
        this.municipalityInformation = municipalityInformation;
        this.municipalityDatabaseRepository = municipalityDatabaseRepository;
        this.municipalityMap = municipalityDatabaseRepository.findAllMulti()
                .map(municipalities -> municipalities.stream()
                        .collect(Collectors.toMap(Municipality::getMunicipalityCode, Function.identity())))
                .await()
                .indefinitely();
    }

    private static Map<String, OldAndCurrentMunicipality> aggregatedMunicipalities(Tuple2<Map<String, MunicipalityModel>, Map<String, Set<OldMunicipalityModel>>> oldAndNewMunicipalities) {
        return oldAndNewMunicipalities.getItem1().entrySet()
                .stream()
                .map(keyValue -> new AbstractMap.SimpleImmutableEntry<>(keyValue.getKey(), new OldAndCurrentMunicipality(keyValue.getValue(),
                        oldAndNewMunicipalities.getItem2().get(keyValue.getKey()))))
                .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
    }

    private static String convertToJsonString(NewAndOldMunicipality municipalityModel) throws JsonProcessingException {
        return UtilConverter.getObjectMapper().writeValueAsString(municipalityModel);
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
        Uni<Map<String, Set<OldMunicipalityModel>>> oldMunicipalities = municipalityInformation.readOldMunicipalities();
        Uni<Map<String, MunicipalityModel>> currentMunicipalities = municipalityInformation.readCurrentMunicipalities();
        Uni.combine()
                .all()
                .unis(currentMunicipalities, oldMunicipalities)
                .asTuple()
                .map(ProcessMunicipalityVerticle::aggregatedMunicipalities)
                .map(oldAndCurrentMunicipalityMap -> new NewAndOldMunicipality(calculateNewMunicipalities(oldAndCurrentMunicipalityMap),
                        calculateOldMunicipalities(oldAndCurrentMunicipalityMap)))
                .subscribe()
                .with(oldAndCurrentMunicipalities -> exec(() -> eventBusVerticle.getEventBus()
                        .publish(MUNICIPALITY_CHANNEL.text(), convertToJsonString(oldAndCurrentMunicipalities))));

    }

    /**
     * Calculates a set of new municipalities that do not exist in the current municipality map.
     *
     * <p>This method processes a map of old and current municipalities to identify new municipalities
     * that are not already present in the existing {@code municipalityMap}. It uses the municipality
     * code as the key to determine whether a municipality is new. The resulting set contains the
     * converted {@link Municipality} objects.
     *
     * @param oldAndCurrentMunicipality a map where the key is a string identifier, and the value is
     *                                  an {@link OldAndCurrentMunicipality} object representing old
     *                                  and current municipality data
     * @return a set of {@link Municipality} objects representing the new municipalities
     */
    private Set<Municipality> calculateNewMunicipalities(Map<String, OldAndCurrentMunicipality> oldAndCurrentMunicipality) {
        return oldAndCurrentMunicipality.values()
                .stream()
                .map(OldAndCurrentMunicipality::municipalityModel)
                .filter(municipalityModel -> municipalityMap.get(municipalityModel.municipalityCode()) == null)
                .map(MunicipalityConverter::fromMunicipalityModel)
                .collect(Collectors.toSet());
    }

    /**
     * Calculates a set of old municipalities that are not part of the current municipality map
     * or are derived from historical data.
     *
     * <p>This method identifies municipalities that either no longer exist in the current
     * {@code municipalityMap} or are derived from the old municipality model set in the provided map
     * of old and current municipalities. The resulting set includes converted {@link OldMunicipality} objects.
     *
     * <p>The method uses two main data sources:
     * <ul>
     *   <li>Entries in the {@code municipalityMap} that are not present in the input map.</li>
     *   <li>Old municipality models derived from the input map.</li>
     * </ul>
     *
     * @param oldAndCurrentMunicipality a map where the key is a string identifier, and the value is
     *                                  an {@link OldAndCurrentMunicipality} object representing old
     *                                  and current municipality data
     * @return a set of {@link OldMunicipality} objects representing the old municipalities
     */
    private Set<OldMunicipality> calculateOldMunicipalities(Map<String, OldAndCurrentMunicipality> oldAndCurrentMunicipality) {
        return Stream.concat(
                        municipalityMap.entrySet().stream()
                                .filter(currentMunicipalities -> oldAndCurrentMunicipality.get(currentMunicipalities.getKey()) == null)
                                .map(value -> fromMunicipality(value.getValue())),
                        oldAndCurrentMunicipality.values().stream()
                                .flatMap(value -> fromOldMunicipalityModel(value.oldMunicipalityModelSet())))
                .collect(Collectors.toSet());
    }

}