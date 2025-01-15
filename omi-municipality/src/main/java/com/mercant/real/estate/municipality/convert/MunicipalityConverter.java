package com.mercant.real.estate.municipality.convert;

import com.mercant.real.estate.municipality.entity.Municipality;
import com.mercant.real.estate.municipality.entity.OldMunicipality;
import com.mercant.real.estate.municipality.model.MunicipalityModel;
import com.mercant.real.estate.municipality.model.OldMunicipalityModel;

import java.util.Set;
import java.util.stream.Stream;

import static com.mercant.real.estate.core.util.CollectionUtil.getSet;

public final class MunicipalityConverter {
    private MunicipalityConverter() {
    }

    public static Municipality fromMunicipalityModel(MunicipalityModel municipalityModel) {
        Municipality municipality = new Municipality();
        municipality.setRegionCode(municipalityModel.regionCode());
        municipality.setProvinceCode(municipalityModel.provinceCode());
        municipality.setMunicipalityCode(municipalityModel.municipalityCode());
        municipality.setMunicipalitySigle(municipalityModel.municipalitySigle());
        municipality.setMunicipalityName(municipalityModel.municipalityName());
        municipality.setRegionName(municipalityModel.regionName());
        municipality.setCadastralCode(municipalityModel.cadastralCode());
        municipality.setTerritorialUnitType(municipalityModel.territorialUnitType());
        municipality.setCapitalsMunicipality(municipalityModel.capitalsMunicipality());
        municipality.setLatitude(municipalityModel.latitude());
        municipality.setLongitude(municipalityModel.longitude());
        municipality.setAltitude(municipalityModel.altitude());
        return municipality;
    }


    public static OldMunicipality fromMunicipality(Municipality municipality) {
        //In this method since the municipality comes from the object municipality, i can't know
        // where is the year and the new municipality code
        OldMunicipality oldMunicipality = new OldMunicipality();
        oldMunicipality.setId(municipality.getId());
        oldMunicipality.setMunicipalityCode(municipality.getMunicipalityCode());
        oldMunicipality.setMunicipalityName(municipality.getMunicipalityName());
        return oldMunicipality;
    }

    public static OldMunicipality fromOldMunicipalityModel(OldMunicipalityModel oldMunicipalityModel) {
        OldMunicipality oldMunicipality = new OldMunicipality();
        oldMunicipality.setYear(oldMunicipalityModel.year());
        oldMunicipality.setMunicipalityCode(oldMunicipalityModel.municipalityCode());
        oldMunicipality.setMunicipalityName(oldMunicipalityModel.municipalityName());
        oldMunicipality.setNewMunicipalityCode(oldMunicipalityModel.newMunicipalityCode());
        return oldMunicipality;
    }
    
    public static Stream<OldMunicipality> fromOldMunicipalityModel(Set<OldMunicipalityModel> oldMunicipalityModel) {
        return getSet(oldMunicipalityModel).stream()
                .map(MunicipalityConverter::fromOldMunicipalityModel);
    }
}
