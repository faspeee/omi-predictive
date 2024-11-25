package com.mercant.real.estate.municipality.utils;

import java.io.IOException;

public interface Supplier<T> {
    T get() throws IOException;
}
