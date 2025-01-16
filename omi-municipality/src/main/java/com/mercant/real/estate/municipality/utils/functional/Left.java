package com.mercant.real.estate.municipality.utils.functional;

import java.util.function.Function;

/**
 * Represents the "left" side of an {@link Either} type, encapsulating a value of type {@code L}.
 * <p>
 * Instances of {@code Left} are used to signify the presence of a "left" value, which typically represents
 * an error or failure in operations that return an {@code Either} type.
 * </p>
 *
 * <p><b>Key Characteristics:</b></p>
 * <ul>
 *   <li>The "right" value is always {@code null} in a {@code Left} instance.</li>
 *   <li>The methods {@code isLeft()} and {@code isRight()} are overridden to reflect the state of this instance.</li>
 *   <li>Functional operations like {@code map()} are no-ops for {@code Left} instances, as transformations only apply to the "right" value.</li>
 * </ul>
 *
 * <p><b>Type Parameters:</b></p>
 * <ul>
 *   <li>{@code L}: The type of the "left" value, typically representing an error or failure state.</li>
 *   <li>{@code R}: The type of the "right" value, which is always absent in {@code Left}.</li>
 * </ul>
 *
 * @param <L> The type of the left value.
 * @param <R> The type of the right value (not applicable in this class, always {@code null}).
 * @author [Fabian Aspee Encina]
 * @version 1.0
 * @see Either
 * @see Right
 * @since [Project Version]
 */
public final class Left<L, R> extends Either<L, R> {

    /**
     * Constructs an instance of {@code Left} with the specified "left" value.
     *
     * @param left The value to encapsulate as the "left" value. Cannot be {@code null}.
     * @throws NullPointerException if {@code left} is {@code null}.
     */
    public Left(L left) {
        super(left, null);
    }

    /**
     * Checks if this instance is a "left".
     *
     * @return Always {@code true}, indicating this is a "left" instance.
     */
    @Override
    public boolean isLeft() {
        return true;
    }

    /**
     * Checks if this instance is a "right".
     *
     * @return Always {@code false}, indicating this is not a "right" instance.
     */
    @Override
    public boolean isRight() {
        return false;
    }

    /**
     * Applies a transformation function to the "right" value.
     * <p>
     * Since this is a {@code Left} instance and does not contain a "right" value, the transformation
     * is a no-op, and this instance is returned unchanged.
     * </p>
     *
     * @param <R1>     The type of the new "right" value after transformation (irrelevant for {@code Left}).
     * @param function A function to transform the "right" value (not applied in this case).
     * @return This {@code Left} instance, unchanged.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <R1> Either<L, R1> map(Function<R, R1> function) {
        return (Either<L, R1>) this;
    }
}
