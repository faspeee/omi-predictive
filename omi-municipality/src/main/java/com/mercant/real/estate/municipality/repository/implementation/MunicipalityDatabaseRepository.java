package com.mercant.real.estate.municipality.repository.implementation;

import com.mercant.real.estate.municipality.configuration.DatabaseVerticle;
import com.mercant.real.estate.municipality.entity.Municipality;
import com.mercant.real.estate.municipality.repository.contract.MunicipalityRepository;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Set;

public final class MunicipalityDatabaseRepository implements MunicipalityRepository {
    private final DatabaseVerticle databaseVerticle;

    public MunicipalityDatabaseRepository(DatabaseVerticle databaseVerticle) {
        this.databaseVerticle = databaseVerticle;
    }

    public Uni<List<Municipality>> findAllMulti() {
        return databaseVerticle.getEmf().withSession(session -> session
                .createQuery("FROM Municipality ", Municipality.class)
                .getResultList());
    }

    public Uni<Void> saveAll(Set<Municipality> municipality) {
        return databaseVerticle.getEmf().withSession(session ->
                session.persistAll((Object) municipality.toArray(new Municipality[0])));
    }
}
