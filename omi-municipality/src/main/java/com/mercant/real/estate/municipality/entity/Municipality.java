package com.mercant.real.estate.municipality.entity;

import jakarta.persistence.Column;
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
    @Column(name = "region_code")
    private String regionCode;
    @Column(name = "province_code")
    private String provinceCode;
    @Column(name = "municipality_code")
    private String municipalityCode;
    @Column(name = "municipality_sigle")
    private String municipalitySigle;
    @Column(name = "municipality_name")
    private String municipalityName;
    @Column(name = "region_name")
    private String regionName;
    @Column(name = "cadastral_code")
    private String cadastralCode;
    @Column(name = "territorial_unit_type")
    private String territorialUnitType;
    @Column(name = "capitals_municipality")
    private String capitalsMunicipality;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;
    @Column(name = "altitude")
    private double altitude;

}
