package com.mercant.real.estate.municipality.core;

public sealed interface MunicipalityCore permits MunicipalityProcessVerticle, SplitMunicipalityVerticle {
    void processMunicipality();
}
