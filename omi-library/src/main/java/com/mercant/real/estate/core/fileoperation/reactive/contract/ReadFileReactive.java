package com.mercant.real.estate.core.fileoperation.reactive.contract;

import com.mercant.real.estate.core.fileoperation.contract.FileObject;
import io.smallrye.mutiny.Multi;

import java.util.Optional;
import java.util.function.Predicate;

public interface ReadFileReactive {
    Multi<Optional<FileObject>> readFile(String path);

    Multi<Optional<FileObject>> readFile(String pathValue, Predicate<FileObject> condition);
}
