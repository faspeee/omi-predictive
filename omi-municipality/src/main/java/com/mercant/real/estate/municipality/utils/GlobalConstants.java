package com.mercant.real.estate.municipality.utils;

import java.util.HashMap;
import java.util.Map;

public final class GlobalConstants {
    private static final Map<String, String> CONSTANT = new HashMap<>();

    private GlobalConstants() {
    }

    public static GlobalConstants getInstance() {
        return GlobalConstantsSingleton.globalConstants;
    }

    public void setAllConstant(Map<String, String> allConstant) {
        CONSTANT.putAll(allConstant);
    }

    public String getConstant(String key) {
        return CONSTANT.get(key);
    }

    private static class GlobalConstantsSingleton {
        private static final GlobalConstants globalConstants = new GlobalConstants();
    }
}
