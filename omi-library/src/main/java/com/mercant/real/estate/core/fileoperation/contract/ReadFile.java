package com.mercant.real.estate.core.fileoperation.contract;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ReadFile {
    Optional<List<FileObject>> readFile(String path);

    Optional<List<FileObject>> readFile(String pathValue, Predicate<FileObject> condition);

    <K> Optional<Map<K, List<FileObject>>> readFile(String path, Predicate<Map.Entry<K, FileObject>> condition,
                                                    Function<FileObject, K> function);
}
