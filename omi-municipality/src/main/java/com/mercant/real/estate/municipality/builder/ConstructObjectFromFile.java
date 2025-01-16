package com.mercant.real.estate.municipality.builder;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.zip.ZipInputStream;

/**
 * A utility class for constructing objects from file data, with customizable filtering and processing logic.
 *
 * <p>This class is designed to process files, apply specific filters on their content, and construct objects
 * from the data. It provides flexibility through functional interfaces for object construction,
 * file content processing, and line filtering.
 *
 * @param <O> the type of objects constructed from the file data
 */
@Builder
@Getter
public final class ConstructObjectFromFile<O> {
    /**
     * A function that constructs an object of type {@code O} from a {@link String}.
     *
     * <p>This function is applied to lines of a file (or other string data) to produce objects
     * of the specified type {@code O}.
     */
    private final Function<String, O> constructObject;

    /**
     * A bifunction that processes the content of a ZIP file to produce a set of objects of type {@code O}.
     *
     * <p>The bifunction takes a {@link ZipInputStream} representing the ZIP file content and the current
     * {@link ConstructObjectFromFile} instance to customize the behavior of the processing logic.
     */
    private final BiFunction<ZipInputStream, ConstructObjectFromFile<O>, Set<O>> functionRead;

    /**
     * A predicate for filtering lines of a file based on specific conditions.
     *
     * <p>If this predicate is {@code null}, all lines will pass the filter. Otherwise, only lines that satisfy
     * the predicate will be processed.
     */
    private final Predicate<String> lineFilter;

    /**
     * The number of lines to skip at the beginning of a file before processing.
     *
     * <p>This is useful for skipping headers or metadata in files. The default value can be specified when
     * building the object.
     */
    private final int linesToSkip;

    /**
     * The expected file extension of the files to be processed.
     *
     * <p>This value can be used to validate whether a file is compatible with the processing logic. For example,
     * files with a specific extension like ".csv" or ".txt" may be required.
     */
    private final String fileExtension;

    /**
     * Retrieves the line filter, ensuring a default filter is returned if no filter was provided.
     *
     * <p>If the {@code lineFilter} is {@code null}, a default predicate is returned that allows all lines
     * to pass through. Otherwise, the provided predicate is returned.
     *
     * @return a {@link Predicate} to filter lines of a file
     */
    public Predicate<String> getLineFilter() {
        return lineFilter == null ? str -> true : lineFilter;
    }
}
