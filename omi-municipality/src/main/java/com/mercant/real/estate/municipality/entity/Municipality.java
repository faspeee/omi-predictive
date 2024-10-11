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

}
