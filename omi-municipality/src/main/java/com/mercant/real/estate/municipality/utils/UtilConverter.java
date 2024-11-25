package com.mercant.real.estate.municipality.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public final class UtilConverter {
    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private UtilConverter() {
    }

}
