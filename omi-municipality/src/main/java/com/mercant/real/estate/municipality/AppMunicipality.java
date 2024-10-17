package com.mercant.real.estate.municipality;

import com.mercant.real.estate.municipality.core.implementation.MunicipalityProcessVerticle;
import com.mercant.real.estate.municipality.core.implementation.SplitMunicipalityVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.mutiny.core.Vertx;

public final class AppMunicipality {
    public static void main(String[] args) {
        var vertx = Vertx.vertx();
        System.out.println("Deployment Starting");
        SplitMunicipalityVerticle splitMunicipalityVerticle = new SplitMunicipalityVerticle();
        vertx.deployVerticleAndAwait(splitMunicipalityVerticle, new DeploymentOptions());
        MunicipalityProcessVerticle municipalityProcessVerticle = new MunicipalityProcessVerticle();
        vertx.deployVerticleAndAwait(municipalityProcessVerticle, new DeploymentOptions());
        municipalityProcessVerticle.processMunicipality();
        splitMunicipalityVerticle.processMunicipality();
        System.out.println("Deployment completed");
    }
}
