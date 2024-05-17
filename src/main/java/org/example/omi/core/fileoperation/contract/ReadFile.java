package org.example.omi.core.fileoperation.contract;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface ReadFile {
    Optional<List<FileObject>> readFile(String path);

    Optional<List<? extends FileObject>> readFile(String pathValue, Predicate<? super FileObject> condition);
}
