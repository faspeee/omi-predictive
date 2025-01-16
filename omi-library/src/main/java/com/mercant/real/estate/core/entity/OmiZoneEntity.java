package com.mercant.real.estate.core.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OmiZoneEntity {
    private String territorialArea;
    private String region;
    private String province;
    private String istatMunicipality;
    private String cadastralMunicipality;
    private String section;
    private String adminMunicipality;
    private String municipalityDescription;
    private String range;
    private String zoneDescription;
    private String zone;
    private String codTipPrev;
    private String descrTipPrev;
    private String statoPrev;
    private String microZone;
    private LocalDateTime localDateTime;
}
