package com.mercant.real.estate.municipality.repository.contract;

import com.mercant.real.estate.municipality.entity.OldMunicipality;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Set;

public interface OldMunicipalityRepository {
    Uni<List<OldMunicipality>> findAll();

    Uni<Void> saveAll(Set<OldMunicipality> oldMunicipalities);
}
