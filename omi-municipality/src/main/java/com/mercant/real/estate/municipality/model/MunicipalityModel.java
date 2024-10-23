package com.mercant.real.estate.municipality.model;

import lombok.Builder;

@Builder
public record MunicipalityModel(String regionCode, String provinceCode, String municipalityCode,
                                String municipalitySigle, String municipalityName, String regionName,
                                String cadastralCode, String territorialUnitType, String capitalsMunicipality,
                                double latitude, double longitude, double altitude) {
}
