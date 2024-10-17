package com.mercant.real.estate.municipality.repository;

import com.mercant.real.estate.municipality.entity.Municipality;
import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.List;

public record MunicipalityRepository(Mutiny.SessionFactory emf) {
    public Uni<List<Municipality>> findAllMulti() {
        return emf.withSession(session -> session
                .createQuery("FROM Municipality ", Municipality.class)
                .getResultList());
    }
}
