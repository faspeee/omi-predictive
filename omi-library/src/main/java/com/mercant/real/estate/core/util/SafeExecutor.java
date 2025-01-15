package com.mercant.real.estate.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercant.real.estate.core.util.functional.Function;
import com.mercant.real.estate.core.util.functional.NothingToVoid;


public final class SafeExecutor {
    private SafeExecutor() {
    }

    public static <I, O> O exec(Function<I, O> function, I element) {
        try {
            return function.apply(element);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public static void exec(NothingToVoid nothingToVoid) {
        try {
            nothingToVoid.call();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
