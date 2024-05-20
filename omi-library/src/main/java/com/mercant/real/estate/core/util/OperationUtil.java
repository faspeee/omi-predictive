package com.mercant.real.estate.core.util;

import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.model.genericmodel.OmiZone;

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

    public static OmiZone createZoneFromLine(String[] elements) {
        return new OmiZone.OmiZoneBuilder()
                .areaTerritoriale(getSafeMode(elements, 0))
                .regione(getSafeMode(elements, 1))
                .prov(getSafeMode(elements, 2))
                .comuneISTAT(getSafeMode(elements, 3))
                .comuneCat(getSafeMode(elements, 4))
                .sez(getSafeMode(elements, 5))
                .comuneAmm(getSafeMode(elements, 6))
                .comuneDescrizione(getSafeMode(elements, 7))
                .fascia(getSafeMode(elements, 8))
                .zonaDescr(getSafeMode(elements, 9))
                .zona(getSafeMode(elements, 10))
                .linkZona(getSafeMode(elements, 11))
                .codTipPrev(getSafeMode(elements, 12))
                .descrTipPrev(getSafeMode(elements, 13))
                .statoPrev(getSafeMode(elements, 14))
                .microzona(getSafeMode(elements, 15))
                .build();
    }

    public static OmiValue createValuesFromLine(String[] elements) {
        return new OmiValue.ValueOmiBuilder()
                .areaTerritoriale(getSafeMode(elements, 0))
                .regione(getSafeMode(elements, 1))
                .prov(getSafeMode(elements, 2))
                .comuneISTAT(getSafeMode(elements, 3))
                .comuneCat(getSafeMode(elements, 4))
                .sez(getSafeMode(elements, 5))
                .comuneAmm(getSafeMode(elements, 6))
                .comuneDescrizione(getSafeMode(elements, 7))
                .fascia(getSafeMode(elements, 8))
                .zona(getSafeMode(elements, 9))
                .linkZona(getSafeMode(elements, 10))
                .codTip(getSafeMode(elements, 11))
                .descrTipologia(getSafeMode(elements, 12))
                .stato(getSafeMode(elements, 13))
                .statoPrev(getSafeMode(elements, 14))
                .comprMin(getSafeMode(elements, 15))
                .comprMax(getSafeMode(elements, 16))
                .supNLCompr(getSafeMode(elements, 17))
                .locMin(getSafeMode(elements, 18))
                .locMax(getSafeMode(elements, 19))
                .supNLLoc(getSafeMode(elements, 20))
                .build();
    }
}
