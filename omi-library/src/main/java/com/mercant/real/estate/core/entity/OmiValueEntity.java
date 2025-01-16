package com.mercant.real.estate.core.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class OmiValueEntity {
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
