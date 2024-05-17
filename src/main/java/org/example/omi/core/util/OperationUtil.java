package org.example.omi.core.util;

public final class OperationUtil {
    private OperationUtil() {
    }

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
