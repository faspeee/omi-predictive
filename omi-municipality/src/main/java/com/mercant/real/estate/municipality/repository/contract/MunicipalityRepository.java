package com.mercant.real.estate.municipality.repository.contract;

import com.mercant.real.estate.municipality.entity.Municipality;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Set;

public interface MunicipalityRepository {
    Uni<List<Municipality>> findAllMulti();

    Uni<Void> saveAll(Set<Municipality> municipality);
}
