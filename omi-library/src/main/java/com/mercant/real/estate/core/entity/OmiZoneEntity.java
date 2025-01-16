package com.mercant.real.estate.core.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class OmiZoneEntity {
    private String zoneDescription;
    private String statoPrev;
    private String microZone;
    private LocalDateTime localDateTime;
}
