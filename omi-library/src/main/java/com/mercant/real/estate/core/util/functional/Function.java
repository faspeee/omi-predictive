package com.mercant.real.estate.core.util.functional;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Function<I, O> {
    O apply(I input) throws JsonProcessingException;
}
