package com.mercant.real.estate.municipality.model;

import lombok.Builder;

@Builder
public record OldMunicipalityModel(int year, String municipalityCode, String municipalityName,
                                   int newMunicipalityCode) {
}
