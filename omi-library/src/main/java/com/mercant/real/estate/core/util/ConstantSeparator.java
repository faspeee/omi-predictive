package com.mercant.real.estate.core.util;

public enum ConstantSeparator {
    COLON {
        @Override
        public String separator() {
            return ",";
        }
    },
    SEMICOLON {
        @Override
        public String separator() {
            return ";";
        }
    },
    DOT {
        @Override
        public String separator() {
            return ".";
        }
    };

    public abstract String separator();
}
