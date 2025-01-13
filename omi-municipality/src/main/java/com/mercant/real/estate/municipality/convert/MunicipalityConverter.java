package com.mercant.real.estate.municipality.convert;

import com.mercant.real.estate.municipality.entity.Municipality;
import com.mercant.real.estate.municipality.model.MunicipalityModel;

public final class MunicipalityConverter {
    private MunicipalityConverter() {
    }

    public static Municipality fromMunicipalityModel(MunicipalityModel municipalityModel) {
        Municipality municipality = new Municipality();
        municipality.setCapitalsMunicipality(municipalityModel.capitalsMunicipality());
        return municipality;
    }
}
