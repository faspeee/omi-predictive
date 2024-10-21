package com.mercant.real.estate.municipality.repository;

import com.mercant.real.estate.municipality.configuration.DatabaseVerticle;
import com.mercant.real.estate.municipality.entity.Municipality;
import io.smallrye.mutiny.Uni;

import java.util.List;

public record MunicipalityRepository(DatabaseVerticle databaseVerticle) {
    public Uni<List<Municipality>> findAllMulti() {
        return databaseVerticle.getEmf().withSession(session -> session
                .createQuery("FROM Municipality ", Municipality.class)
                .getResultList());
    }
}
