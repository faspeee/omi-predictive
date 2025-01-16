package com.mercant.real.estate.municipality.utils.functional;

import java.util.Optional;
import java.util.function.Function;

/**
 * Represents a disjoint union type that encapsulates a value that can either be of type {@code L} (left) or {@code R} (right).
 * <p>
 * This abstraction is useful for modeling operations that can return two mutually exclusive types of results,
 * such as success ({@code R}) or failure ({@code L}). The {@code Either} class is immutable and provides
 * a functional-style API to work with its encapsulated value.
 * </p>
 *
 * <p><b>Key Features:</b></p>
 * <ul>
 *   <li>Supports the presence of a value on either the left or right side but not both simultaneously.</li>
 *   <li>Provides utility methods to query, map, and transform the encapsulated value.</li>
 *   <li>Offers functional-style operations for working with the "right" value, while the "left" value typically represents errors.</li>
 * </ul>
 *
 * <p><b>Type Parameters:</b></p>
 * <ul>
 *   <li>{@code L}: The type of the "left" value, typically representing an error or failure state.</li>
 *   <li>{@code R}: The type of the "right" value, typically representing a success state.</li>
 * </ul>
 *
 * @param <L> The type of the left value.
 * @param <R> The type of the right value.
 * @author [Fabian Aspee Encina]
 * @version 1.0
 * @see Optional
 * @see Function
 * @since [Project Version]
 */
public abstract class Either<L, R> {
    private final L left;
    private final R right;

    /**
     * Constructs an instance of {@code Either}.
     *
     * @param left  The left value (can be {@code null} if the right value is present).
     * @param right The right value (can be {@code null} if the left value is present).
     */
    protected Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Converts the "right" value of this {@code Either} into an {@link Optional}.
     * <p>
     * If the "right" value is present, it returns an {@code Optional} containing the value;
     * otherwise, it returns an empty {@code Optional}.
     * </p>
     *
     * @return An {@code Optional} containing the "right" value if present, or an empty {@code Optional} otherwise.
     */
    protected Optional<R> toOptional() {
        if (right == null) {
            return Optional.empty();
        } else {
            return Optional.of(right);
        }
    }

    /**
     * Retrieves the "left" value of this {@code Either} as an {@link Optional}.
     * <p>
     * This method allows safe access to the "left" value, which typically represents an error or failure state.
     * </p>
     *
     * @return An {@code Optional} containing the "left" value if present, or an empty {@code Optional} otherwise.
     */
    public Optional<L> left() {
        return Optional.ofNullable(left);
    }

    /**
     * Retrieves the "right" value of this {@code Either} as an {@link Optional}.
     * <p>
     * This method allows safe access to the "right" value, which typically represents a success state.
     * </p>
     *
     * @return An {@code Optional} containing the "right" value if present, or an empty {@code Optional} otherwise.
     */
    public Optional<R> right() {
        return Optional.ofNullable(right);
    }

    /**
     * Determines if this {@code Either} contains a "left" value.
     *
     * @return {@code true} if this {@code Either} contains a "left" value, {@code false} otherwise.
     */
    public abstract boolean isLeft();

    /**
     * Determines if this {@code Either} contains a "right" value.
     *
     * @return {@code true} if this {@code Either} contains a "right" value, {@code false} otherwise.
     */
    public abstract boolean isRight();

    /**
     * Transforms the "right" value of this {@code Either} using the provided mapping function.
     * <p>
     * If this {@code Either} contains a "right" value, the function is applied to it, and the result is wrapped
     * in a new {@code Either}. If this {@code Either} contains a "left" value, it remains unchanged.
     * </p>
     *
     * @param <R1>     The type of the transformed "right" value.
     * @param function A mapping function to apply to the "right" value.
     * @return A new {@code Either} containing the transformed "right" value, or the original "left" value if present.
     */
    public abstract <R1> Either<L, R1> map(Function<R, R1> function);
}
