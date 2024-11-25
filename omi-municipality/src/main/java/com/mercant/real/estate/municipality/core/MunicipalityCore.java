package com.mercant.real.estate.municipality.core;

public sealed interface MunicipalityCore permits MunicipalityFinalProcessVerticle, ProcessMunicipalityVerticle {
    void processMunicipality();
}
