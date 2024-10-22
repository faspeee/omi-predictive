package com.mercant.real.estate.municipality.model;

import lombok.Builder;

@Builder
public class MunicipalityModel {
    private String regionCode;
    private String provinceCode;
    private String municipalityCode;
    private String municipalitySigle;
    private String municipalityName;
    private String regionName;
    private String cadastralCode;
    private String territorialUnitType;
    private String capitalsMunicipality;
    private double latitude;
    private double longitude;
    private double altitude;

}
