package com.mercant.real.estate.core.fileoperation.reactive.implementation;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

abstract class AbstractFileReactive<T> {
    protected Multi<Optional<T>> readFile(String path, int skipLine) {
        return Vertx.vertx().fileSystem().readFile(path).toMulti()
                .flatMap(buffer -> Multi.createFrom().items(Arrays.stream(buffer.toString().split("\n")).skip(skipLine)))
                .map(line -> constructObject(String.valueOf(line)))
                .onFailure().call(() -> Uni.createFrom().item(Optional.empty()));
    }


    protected Multi<Optional<T>> readFile(String path, int skipLine, Predicate<T> condition) {
        return Vertx.vertx().fileSystem().readFile(path).toMulti()
                .flatMap(buffer -> Multi.createFrom().items(Arrays.stream(buffer.toString().split("\n")).skip(skipLine)))
                .map(line -> constructObject(String.valueOf(line)))
                .filter(element -> element.isPresent() && condition.test(element.get()))
                .onFailure().call(() -> Uni.createFrom().item(Optional.empty()));
    }

    protected abstract Optional<T> constructObject(String line);
}
