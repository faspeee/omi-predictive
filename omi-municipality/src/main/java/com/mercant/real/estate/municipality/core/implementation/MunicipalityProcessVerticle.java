package com.mercant.real.estate.municipality.core.implementation;

import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.core.contract.MunicipalityCore;
import com.mercant.real.estate.municipality.repository.MunicipalityRepository;
import com.mercant.real.estate.municipality.repository.OldMunicipalityRepository;
import com.mercant.real.estate.municipality.utils.Logger;

import static com.mercant.real.estate.municipality.utils.Constant.MUNICIPALITY_CHANNEL;

/**
 * The {@code MunicipalityProcessVerticle} class extends the {@code Common} class
 * and implements the {@code MunicipalityCore} interface. This class is responsible
 * for managing the processing of municipality-related tasks within a Vert.x application.
 *
 * <p>
 * This verticle establishes a connection to a PostgreSQL database using Hibernate
 * Reactive and listens for messages on a specific Event Bus channel for processing
 * municipality data.
 * </p>
 *
 * <p>
 * It initializes the Hibernate SessionFactory during its asynchronous startup phase
 * and sets up a consumer for processing municipality messages.
 * </p>
 *
 * @see MunicipalityCore
 * @see io.vertx.mutiny.core.eventbus.EventBus
 */
public final class MunicipalityProcessVerticle implements MunicipalityCore {
    private final EventBusVerticle eventBusVerticle;
    private final WebClientVerticle webClientVerticle;
    private final MunicipalityRepository municipalityRepository;
    private final OldMunicipalityRepository oldMunicipalityRepository;

    public MunicipalityProcessVerticle(EventBusVerticle eventBusVerticle, WebClientVerticle webClientVerticle, MunicipalityRepository municipalityRepository, OldMunicipalityRepository oldMunicipalityRepository) {
        this.eventBusVerticle = eventBusVerticle;
        this.webClientVerticle = webClientVerticle;
        this.municipalityRepository = municipalityRepository;
        this.oldMunicipalityRepository = oldMunicipalityRepository;
    }

    @Override
    public void processMunicipality() {
        eventBusVerticle.getEventBus().consumer(MUNICIPALITY_CHANNEL, message -> {
            Logger.info(message.body().toString());
        });
    }
}