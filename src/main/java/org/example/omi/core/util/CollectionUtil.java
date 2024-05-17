package org.example.omi.core.util;

import java.util.List;
import java.util.function.Function;

public class CollectionUtil {
    public static <R, T> List<T> castListObject(List<R> list, Function<R, T> functionCast) {
        return list.stream().map(functionCast).toList();
    }
}
