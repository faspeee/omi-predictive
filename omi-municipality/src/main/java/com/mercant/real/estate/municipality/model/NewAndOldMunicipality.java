package com.mercant.real.estate.municipality.model;

import com.mercant.real.estate.municipality.entity.Municipality;
import com.mercant.real.estate.municipality.entity.OldMunicipality;

import java.util.Set;

public record NewAndOldMunicipality(Set<Municipality> municipalityModel, Set<OldMunicipality> oldMunicipalities) {
}
