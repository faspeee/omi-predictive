package com.mercant.real.estate.municipality;

import com.mercant.real.estate.municipality.configuration.DatabaseVerticle;
import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.core.contract.MunicipalityCore;
import com.mercant.real.estate.municipality.core.implementation.MunicipalityProcessVerticle;
import com.mercant.real.estate.municipality.core.implementation.SplitMunicipalityVerticle;
import com.mercant.real.estate.municipality.repository.MunicipalityRepository;
import com.mercant.real.estate.municipality.repository.OldMunicipalityRepository;
import io.vertx.core.DeploymentOptions;
import io.vertx.mutiny.core.Vertx;

public final class AppMunicipality {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        System.out.println("Deployment Starting");
        EventBusVerticle eventBusVerticle = new EventBusVerticle();
        WebClientVerticle webClientVerticle = new WebClientVerticle();
        DatabaseVerticle databaseVerticle = new DatabaseVerticle();
        vertx.deployVerticleAndAwait(eventBusVerticle, new DeploymentOptions());
        vertx.deployVerticleAndAwait(webClientVerticle, new DeploymentOptions());
        vertx.deployVerticleAndAwait(databaseVerticle, new DeploymentOptions());
        MunicipalityRepository municipalityRepository = new MunicipalityRepository(databaseVerticle);
        OldMunicipalityRepository oldMunicipalityRepository = new OldMunicipalityRepository(databaseVerticle);
        MunicipalityProcessVerticle municipalityProcessVerticle = new MunicipalityProcessVerticle(eventBusVerticle,
                webClientVerticle, municipalityRepository, oldMunicipalityRepository);
        MunicipalityCore splitMunicipalityVerticle = new SplitMunicipalityVerticle(eventBusVerticle, webClientVerticle);
        municipalityProcessVerticle.processMunicipality();
        splitMunicipalityVerticle.processMunicipality();
        System.out.println("Deployment completed");
    }
}
