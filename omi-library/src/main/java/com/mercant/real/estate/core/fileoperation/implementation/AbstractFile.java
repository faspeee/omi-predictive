package com.mercant.real.estate.core.fileoperation.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

abstract class AbstractFile<T> {

    protected Optional<List<T>> readFile(String path, int skipLine) {
        try (BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8))) {
            List<T> list = bufferedReader.lines()
                    .skip(skipLine)
                    .map(this::constructObject)
                    .toList();
            return Optional.of(list);
        } catch (UncheckedIOException | IOException e) {
            return Optional.empty();
        }
    }

    protected Optional<List<T>> readFile(String path, int skipLine, Predicate<T> condition) {
        try (BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8))) {
            List<T> list = bufferedReader.lines()
                    .skip(skipLine)
                    .map(this::constructObject)
                    .filter(condition)
                    .toList();
            return Optional.of(list);
        } catch (UncheckedIOException | IOException e) {
            return Optional.empty();
        }
    }

    protected <K> Optional<Map<K, List<T>>> readFile(String path, int skipLine, Predicate<Map.Entry<K, T>> condition, Function<T, K> function) {
        try (BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8))) {
            Map<K, List<T>> listMap = bufferedReader.lines()
                    .skip(skipLine)
                    .map(line -> {
                        T object = constructObject(line);
                        return new AbstractMap.SimpleImmutableEntry<>(function.apply(object), object);
                    })
                    .filter(condition)
                    .collect(Collectors.groupingBy(Map.Entry::getKey,
                            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
            return Optional.of(listMap);
        } catch (UncheckedIOException | IOException e) {
            return Optional.empty();
        }
    }

    protected abstract T constructObject(String line);
}
