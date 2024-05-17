package org.example.omi.core.fileoperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

abstract class AbstractFile {
    protected Optional<List<FileObject>> readFile(String path, int skipLine) {
        try (BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8))) {
            List<FileObject> list = bufferedReader.lines()
                    .skip(skipLine)
                    .map(this::constructObject)
                    .toList();
            return Optional.of(list);
        } catch (UncheckedIOException | IOException e) {
            return Optional.empty();
        }
    }

    protected abstract FileObject constructObject(String line);
}
