package com.mercant.real.estate.municipality.repository;

import com.mercant.real.estate.municipality.entity.OldMunicipality;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class OldMunicipalityRepository implements PanacheRepository<OldMunicipality> {
    public Uni<List<OldMunicipality>> findAllMulti() {
        return listAll();
    }
}
