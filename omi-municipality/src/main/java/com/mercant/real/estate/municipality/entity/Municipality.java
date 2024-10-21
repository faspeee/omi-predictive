package com.mercant.real.estate.municipality.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Municipality {
    @Id
    private Long id;
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
