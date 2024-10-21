package com.mercant.real.estate.municipality.repository;

import com.mercant.real.estate.municipality.configuration.DatabaseVerticle;
import com.mercant.real.estate.municipality.entity.OldMunicipality;
import io.smallrye.mutiny.Uni;

import java.util.List;

public record OldMunicipalityRepository(DatabaseVerticle databaseVerticle) {

    public Uni<List<OldMunicipality>> findAll() {
        return databaseVerticle.getEmf().withSession(session -> session
                .createQuery("FROM OldMunicipality", OldMunicipality.class)
                .getResultList());
    }

}
