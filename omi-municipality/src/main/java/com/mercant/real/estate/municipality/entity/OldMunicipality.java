package com.mercant.real.estate.municipality.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Entity
@Getter
public class OldMunicipality {
    @Id
    private Long id;
    @Column(name = "year")
    private int year;
    @Column(name = "municipality_code")
    private String municipalityCode;
    @Column(name = "municipality_name")
    private String municipalityName;
    @Column(name = "new_municipality_code")
    private int newMunicipalityCode;

}
