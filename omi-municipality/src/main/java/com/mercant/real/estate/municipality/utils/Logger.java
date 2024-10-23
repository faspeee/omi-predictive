package com.mercant.real.estate.municipality.utils;

import io.vertx.core.impl.logging.LoggerFactory;

public final class Logger {
    private final static io.vertx.core.impl.logging.Logger logger = LoggerFactory.getLogger(Logger.class);

    private Logger() {
    }

    public static void info(String message) {
        System.out.println(message);
        logger.info(message);
    }
}
