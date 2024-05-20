package com.mercant.real.estate.core.util;

import io.smallrye.mutiny.Multi;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CollectionUtil {
    public static <R, T> List<T> castListObject(List<R> list, Function<R, T> functionCast) {
        return list.stream().map(functionCast).toList();
    }

    public static <R, T> Multi<Optional<T>> castMultiObject(Multi<Optional<R>> list, Function<R, T> functionCast) {
        return list.map(optional -> optional.map(functionCast));
    }
}
