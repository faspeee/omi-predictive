package com.mercant.real.estate.municipality;

import com.mercant.real.estate.municipality.configuration.DatabaseVerticle;
import com.mercant.real.estate.municipality.configuration.EventBusVerticle;
import com.mercant.real.estate.municipality.configuration.FileDatabaseVerticle;
import com.mercant.real.estate.municipality.configuration.WebClientVerticle;
import com.mercant.real.estate.municipality.core.MunicipalityCore;
import com.mercant.real.estate.municipality.core.MunicipalityFinalProcessVerticle;
import com.mercant.real.estate.municipality.core.ProcessMunicipalityVerticle;
import com.mercant.real.estate.municipality.repository.implementation.MunicipalityDatabaseRepository;
import com.mercant.real.estate.municipality.repository.implementation.OldMunicipalityDatabaseRepository;
import com.mercant.real.estate.municipality.webinformation.MunicipalityInformation;
import io.vertx.core.DeploymentOptions;
import io.vertx.mutiny.core.Vertx;

public final class AppMunicipality {
    public static void main(String[] args) {
        System.getProperties().put("local", "true");
        var vertx = Vertx.vertx();
        System.out.println("Deployment Starting");
        EventBusVerticle eventBusVerticle = new EventBusVerticle();
        WebClientVerticle webClientVerticle = new WebClientVerticle();
        DatabaseVerticle postgresDatabaseVerticle = new FileDatabaseVerticle();
        vertx.deployVerticleAndAwait(eventBusVerticle, new DeploymentOptions());
        vertx.deployVerticleAndAwait(webClientVerticle, new DeploymentOptions());
        vertx.deployVerticleAndAwait(postgresDatabaseVerticle, new DeploymentOptions());
        MunicipalityDatabaseRepository municipalityDatabaseRepository = new MunicipalityDatabaseRepository(postgresDatabaseVerticle);
        OldMunicipalityDatabaseRepository oldMunicipalityDatabaseRepository = new OldMunicipalityDatabaseRepository(postgresDatabaseVerticle);
        MunicipalityFinalProcessVerticle municipalityProcessVerticle = new MunicipalityFinalProcessVerticle(eventBusVerticle,
                webClientVerticle, municipalityDatabaseRepository, oldMunicipalityDatabaseRepository);
        MunicipalityInformation municipalityInformation = new MunicipalityInformation(webClientVerticle);
        MunicipalityCore splitMunicipalityVerticle = new ProcessMunicipalityVerticle(eventBusVerticle, municipalityInformation, municipalityDatabaseRepository);
        municipalityProcessVerticle.processMunicipality();
        splitMunicipalityVerticle.processMunicipality();
        System.out.println("Deployment completed");
    }
}
