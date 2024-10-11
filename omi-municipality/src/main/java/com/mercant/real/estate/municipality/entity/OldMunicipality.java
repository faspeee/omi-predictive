package com.mercant.real.estate.municipality.entity;

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

}
