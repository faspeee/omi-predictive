package com.mercant.real.estate.core.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OmiValueEntity {
    private String territorialArea;
    private String region;
    private String province;
    private String istatMunicipality;
    private String cadastralMunicipality;
    private String section;
    private String adminMunicipality;
    private String descriptionMunicipality;
    private String range;
    private String zone;
    private String linkZone;
    private String typologyCode;
    private String typologyDescription;
    private String state;
    private String preventionState;
    private String minimalBuy;
    private String maximalBuy;
    private String supNLCompr;
    private String minimalRental;
    private String maximalRental;
    private String supNLLoc;
    private LocalDateTime localDateTime;
}
