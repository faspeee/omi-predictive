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
import com.mercant.real.estate.municipality.utils.GlobalConstants;
import com.mercant.real.estate.municipality.utils.Logger;
import com.mercant.real.estate.municipality.utils.functional.Either;
import com.mercant.real.estate.municipality.utils.functional.Left;
import com.mercant.real.estate.municipality.utils.functional.Right;
import com.mercant.real.estate.municipality.webinformation.GoogleApi;
import com.mercant.real.estate.municipality.webinformation.MunicipalityInformation;
import io.vertx.core.DeploymentOptions;
import io.vertx.mutiny.core.Vertx;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mercant.real.estate.core.util.ConstantSeparator.EQUALS;
import static com.mercant.real.estate.municipality.utils.Constant.ERROR_MSG_TEMPLATE;

public final class AppMunicipality {
    public static void main(String[] args) {
        Either<String, Map<String, String>> globalVariables = setGlobalVariables(args);
        if (globalVariables.isLeft()) {
            globalVariables.left().ifPresent(Logger::info);
            System.exit(0);
        } else {
            var vertx = Vertx.vertx();
            globalVariables.right()
                    .ifPresent(map -> GlobalConstants.getInstance().setAllConstant(map));
            Logger.info("Deployment Starting");
            EventBusVerticle eventBusVerticle = new EventBusVerticle();
            WebClientVerticle webClientVerticle = new WebClientVerticle();
            DatabaseVerticle postgresDatabaseVerticle = new FileDatabaseVerticle();
            vertx.deployVerticleAndAwait(eventBusVerticle, new DeploymentOptions());
            vertx.deployVerticleAndAwait(webClientVerticle, new DeploymentOptions());
            vertx.deployVerticleAndAwait(postgresDatabaseVerticle, new DeploymentOptions());
            MunicipalityDatabaseRepository municipalityDatabaseRepository = new MunicipalityDatabaseRepository(postgresDatabaseVerticle);
            OldMunicipalityDatabaseRepository oldMunicipalityDatabaseRepository = new OldMunicipalityDatabaseRepository(postgresDatabaseVerticle);
            MunicipalityFinalProcessVerticle municipalityProcessVerticle = new MunicipalityFinalProcessVerticle(eventBusVerticle,
                    new GoogleApi(webClientVerticle), municipalityDatabaseRepository, oldMunicipalityDatabaseRepository);
            MunicipalityInformation municipalityInformation = new MunicipalityInformation(webClientVerticle);
            MunicipalityCore splitMunicipalityVerticle = new ProcessMunicipalityVerticle(eventBusVerticle, municipalityInformation, municipalityDatabaseRepository);
            municipalityProcessVerticle.processMunicipality();
            splitMunicipalityVerticle.processMunicipality();
            Logger.info("Deployment completed");
        }

    }

    private static Either<String, Map<String, String>> setGlobalVariables(String[] args) {
        String resultError = Arrays.stream(args)
                .filter(line -> {
                    String[] split = line.split(EQUALS.separator());
                    return split.length != 2 || split[0].isEmpty();
                })
                .map(line -> ERROR_MSG_TEMPLATE.text() + line)
                .collect(Collectors.joining(";"));
        if (resultError.isEmpty()) {
            Map<String, String> globalArgs = Arrays.stream(args)
                    .map(line -> {
                        String[] strings = line.split(EQUALS.separator());
                        return new AbstractMap.SimpleImmutableEntry<>(strings[0], strings[1]);
                    })
                    .collect(Collectors.toMap(AbstractMap.SimpleImmutableEntry::getKey, AbstractMap.SimpleImmutableEntry::getValue));
            return new Right<>(globalArgs);
        } else {
            return new Left<>(resultError);
        }
    }
}
