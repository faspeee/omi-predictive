package com.mercant.real.estate.core.fileoperation.reactive.implementation;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

abstract class AbstractFileReactive<T> {
    protected Multi<Optional<T>> readFile(String path, int skipLine) {
        return Vertx.vertx().fileSystem().readFile(path)
                .map(buffer -> Multi.createFrom()
                        .emitter(multiEmitter -> Arrays.stream(buffer.toString().split("\n"))
                                .parallel()
                                .skip(skipLine)
                                .forEach(multiEmitter::emit))).toMulti()
                .flatMap(multi -> multi
                        .map(line -> constructObject(String.valueOf(line)))
                        .onFailure().call(() -> Uni.createFrom().item(Optional.empty())));
    }


    protected Multi<Optional<T>> readFile(String path, int skipLine, Predicate<T> condition) {
        return Vertx.vertx().fileSystem().readFile(path)
                .map(buffer -> Multi.createFrom()
                        .emitter(multiEmitter -> Arrays.stream(buffer.toString().split("\n"))
                                .parallel()
                                .skip(skipLine)
                                .map(this::constructObject)
                                .filter(element -> element.isPresent() && condition.test(element.get()))
                                .forEach(multiEmitter::emit))).toMulti()
                .flatMap(multi -> multi
                        .map(line -> constructObject(String.valueOf(line)))
                        .onFailure().call(() -> Uni.createFrom().item(Optional.empty())));
    }

    protected abstract Optional<T> constructObject(String line);
}
