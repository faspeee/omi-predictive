package com.mercant.real.estate.municipality.repository;

import com.mercant.real.estate.municipality.entity.OldMunicipality;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;

public record OldMunicipalityRepository(Mutiny.SessionFactory emf) {

    public Uni<List<OldMunicipality>> findAll() {
        return emf.withSession(session -> session
                .createQuery("FROM OldMunicipality", OldMunicipality.class)
                .getResultList());
    }

}
