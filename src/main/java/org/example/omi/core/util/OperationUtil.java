package org.example.omi.core.util;

public final class OperationUtil {
    private OperationUtil() {
    }

    /**
     * Retrieves an element from the given array safely. If the index is out of bounds, it returns an empty string.
     *
     * @param elements the array of elements.
     * @param index    the index of the element to retrieve.
     * @return the element at the specified index, or an empty string if the index is out of bounds.
     */
    public static String getSafeMode(String[] elements, int index) {
        try {
            return elements[index];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return null;
        }
    }

    public static int getSafeModeInt(String[] elements, int index) {
        try {
            return Integer.parseInt(elements[index]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException exception) {
            return -1;
        }
    }
}
