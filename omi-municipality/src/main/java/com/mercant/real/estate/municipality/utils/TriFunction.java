package com.mercant.real.estate.municipality.utils;

public interface TriFunction<A, B, C, D> {
    D apply(A a, B b, C c);
}
