package com.mercant.real.estate.core.util;

public enum ConstantExtension {
    JSON_EXTENSION {
        @Override
        public String compoundExtension(String name) {
            return name + ".json";
        }
    },
    TXT_EXTENSION {
        @Override
        public String compoundExtension(String name) {
            return name + ".txt";
        }
    };

    public abstract String compoundExtension(String name);
}
