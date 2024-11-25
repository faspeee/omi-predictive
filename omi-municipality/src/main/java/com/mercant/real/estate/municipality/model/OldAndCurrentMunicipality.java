package com.mercant.real.estate.municipality.model;

import java.util.Set;

public record OldAndCurrentMunicipality(MunicipalityModel municipalityModel,
                                        Set<OldMunicipalityModel> oldMunicipalityModelSet) {
}
