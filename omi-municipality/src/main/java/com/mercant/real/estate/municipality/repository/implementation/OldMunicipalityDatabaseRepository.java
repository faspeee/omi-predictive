package com.mercant.real.estate.municipality.repository.implementation;

import com.mercant.real.estate.municipality.configuration.DatabaseVerticle;
import com.mercant.real.estate.municipality.entity.OldMunicipality;
import com.mercant.real.estate.municipality.repository.contract.OldMunicipalityRepository;
import io.smallrye.mutiny.Uni;

import java.util.List;

public final class OldMunicipalityDatabaseRepository implements OldMunicipalityRepository {
    private final DatabaseVerticle databaseVerticle;

    public OldMunicipalityDatabaseRepository(DatabaseVerticle databaseVerticle) {
        this.databaseVerticle = databaseVerticle;
    }

    public Uni<List<OldMunicipality>> findAll() {
        return databaseVerticle.getEmf().withSession(session -> session
                .createQuery("FROM OldMunicipality", OldMunicipality.class)
                .getResultList());
    }

}
