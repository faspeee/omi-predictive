package com.mercant.real.estate.municipality.repository;

import com.mercant.real.estate.municipality.entity.Municipality;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MunicipalityRepository implements PanacheRepository<Municipality> {
    public Uni<List<Municipality>> findAllMulti() {
        return listAll();
    }
}
