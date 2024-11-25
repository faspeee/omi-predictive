package com.mercant.real.estate.municipality.utils;

public enum IntConstant {
    LINE_TO_SKIP_CURRENT_MUNICIPALITY {
        @Override
        public int value() {
            return 3;
        }
    }, LINE_TO_SKIP_OLD_MUNICIPALITY {
        @Override
        public int value() {
            return 1;
        }
    };

    public abstract int value();
}
