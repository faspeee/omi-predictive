package com.mercant.real.estate.municipality.utils.functional;

import java.io.IOException;

/**
 * Functional interface representing a supplier of results, often used to defer
 * computation or provide results on demand.
 * <p>
 * This interface allows the production of a result of type {@code T}, which
 * can be any object. Unlike the standard {@link java.util.function.Supplier},
 * this version allows for the supplier to throw a checked {@link Exception}.
 * It is often used in situations where the operation might fail or is dependent
 * on some external system (e.g., I/O operations, network requests).
 * </p>
 *
 * <p>
 * This interface is designed to be implemented by a lambda expression or method reference,
 * and it follows the functional interface pattern as defined in Java 8.
 * </p>
 *
 * <h3>Functional Method</h3>
 * The functional method of this interface is {@link #get()}, which is called to
 * retrieve the result. It may throw an {@link Exception}, so callers need to handle
 * the exception or declare it in the calling method's signature.
 *
 * <pre>{@code
 * // Example usage:
 * Supplier<String> supplier = () -> {
 *     if (someCondition) {
 *         return "Success";
 *     } else {
 *         throw new Exception("Failed to get result");
 *     }
 * };
 *
 * try {
 *     String result = supplier.get();
 *     System.out.println(result);
 * } catch (Exception e) {
 *     e.printStackTrace();
 * }
 * }</pre>
 *
 * @param <T> the type of result supplied by this supplier
 * @see java.util.function.Supplier
 * @see IOException
 */
public interface Supplier<T> {
    /**
     * Retrieves the result of type {@code T}, which might involve deferred computation
     * or other processes that produce the result.
     * <p>
     * This method is expected to be called when the result is needed, and it may
     * throw an {@link IOException} if the operation cannot complete successfully.
     * </p>
     *
     * @return a result of type {@code T}
     * @throws IOException if unable to supply the result due to an error
     */
    T get() throws IOException;
}
