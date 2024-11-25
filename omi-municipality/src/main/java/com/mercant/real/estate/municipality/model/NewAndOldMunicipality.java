package com.mercant.real.estate.municipality.model;

import com.mercant.real.estate.municipality.entity.Municipality;

import java.util.Set;

public record NewAndOldMunicipality(Set<Municipality> municipalityModel, Set<Municipality> oldMunicipalities) {
}
