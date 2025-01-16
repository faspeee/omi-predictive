package com.mercant.real.estate.municipality.utils.functional;

import java.util.function.Function;

/**
 * Represents the "right" side of an {@link Either} type, encapsulating a value of type {@code R}.
 * <p>
 * Instances of {@code Right} are used to signify the presence of a "right" value, which typically represents
 * a successful outcome in operations that return an {@code Either} type.
 * </p>
 *
 * <p><b>Key Characteristics:</b></p>
 * <ul>
 *   <li>The "left" value is always {@code null} in a {@code Right} instance.</li>
 *   <li>The methods {@code isLeft()} and {@code isRight()} are overridden to reflect the state of this instance.</li>
 *   <li>Functional operations like {@code map()} can transform the encapsulated "right" value.</li>
 * </ul>
 *
 * <p><b>Type Parameters:</b></p>
 * <ul>
 *   <li>{@code L}: The type of the "left" value (not applicable in this class, always {@code null}).</li>
 *   <li>{@code R}: The type of the "right" value, typically representing a successful outcome.</li>
 * </ul>
 *
 * @param <L> The type of the left value (not applicable for {@code Right}).
 * @param <R> The type of the right value.
 * @author [Fabian Aspee Encina]
 * @version 1.0
 * @see Either
 * @see Left
 * @since [Project Version]
 */
public final class Right<L, R> extends Either<L, R> {

    /**
     * Constructs an instance of {@code Right} with the specified "right" value.
     *
     * @param right The value to encapsulate as the "right" value. Cannot be {@code null}.
     * @throws NullPointerException if {@code right} is {@code null}.
     */
    public Right(R right) {
        super(null, right);
    }

    /**
     * Checks if this instance is a "left".
     *
     * @return Always {@code false}, indicating this is not a "left" instance.
     */
    @Override
    public boolean isLeft() {
        return false;
    }

    /**
     * Checks if this instance is a "right".
     *
     * @return Always {@code true}, indicating this is a "right" instance.
     */
    @Override
    public boolean isRight() {
        return true;
    }

    /**
     * Applies a transformation function to the "right" value.
     * <p>
     * If the encapsulated "right" value is present, the specified function is applied to transform it,
     * and the result is encapsulated in a new {@code Right} instance. This operation does not affect the "left" value.
     * </p>
     *
     * @param <R1>     The type of the transformed "right" value.
     * @param function A mapping function to apply to the "right" value.
     * @return A new {@code Right} instance containing the transformed "right" value.
     * @throws NullPointerException if the mapping function returns {@code null}.
     */
    @Override
    public <R1> Either<L, R1> map(Function<R, R1> function) {
        return new Right<>(right().map(function).orElse(null));
    }
}

