package com.mercant.real.estate.municipality.builder;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.zip.ZipInputStream;

@Builder
@Getter
public final class ConstructObjectFromFile<O> {
    private final Function<String, O> constructObject;
    private final BiFunction<ZipInputStream, ConstructObjectFromFile<O>, Set<O>> functionRead;
    private final Predicate<String> lineFilter;
    private final int linesToSkip;
    private final String fileExtension;

    public Predicate<String> getLineFilter() {
        return lineFilter == null ? str -> true : lineFilter;
    }
}
