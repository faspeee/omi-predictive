package com.mercant.real.estate.municipality.repository.contract;

import com.mercant.real.estate.municipality.entity.OldMunicipality;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface OldMunicipalityRepository {
    Uni<List<OldMunicipality>> findAll();
}
