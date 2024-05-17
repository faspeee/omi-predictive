package org.example.omi.core.fileoperation.implementation;

import org.example.omi.core.fileoperation.contract.FileObject;
import org.example.omi.core.fileoperation.contract.ReadFile;
import org.example.omi.core.model.OmiZone;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.example.omi.core.util.OperationUtil.getSafeMode;

/**
 * The {@code ReadFileZone} class extends {@link AbstractFile} and implements the {@link ReadFile} interface to provide
 * functionality for reading and parsing files containing {@link OmiZone} objects. This class specifically handles files
 * formatted with a specific structure, where each line represents an {@code OmiZone} object with fields separated by semicolons.
 * <p>
 * The class offers methods to:
 * <ul>
 *     <li>Read files from a specified path and convert them into lists of {@link FileObject} instances.</li>
 *     <li>Apply filtering conditions while reading the files.</li>
 *     <li>Construct {@link OmiZone} objects from individual lines of the file.</li>
 * </ul>
 * <p>
 * Example of a line in the file:
 * <pre>
 * Area_territoriale;Regione;Prov;Comune_ISTAT;Comune_cat;Sez;Comune_amm;Comune_descrizione;Fascia;Zona_Descr;
 * Zona;LinkZona;Cod_tip_prev;Descr_tip_prev;Stato_prev;Microzona;
 * </pre>
 */
public final class ReadFileZone extends AbstractFile implements ReadFile {

    /**
     * Reads a file from the specified path and returns an {@link Optional} containing a list of {@link FileObject} instances.
     * This method delegates the actual reading process to {@link #readFile(String, int)} with a default value of 1 for the limit.
     *
     * @param path the path to the file.
     * @return an {@link Optional} containing a list of {@link FileObject} instances if the file is read successfully, or an empty {@link Optional} otherwise.
     */
    @Override
    public Optional<List<FileObject>> readFile(String path) {
        return readFile(path, 1);
    }

    /**
     * Reads a file from the specified path and applies a filtering condition to each {@link FileObject} instance.
     * This method delegates the actual reading process to {@link #readFile(String, int, Predicate)} with a default value of 1 for the limit.
     *
     * @param pathValue the path to the file.
     * @param condition a {@link Predicate} to filter the {@link FileObject} instances.
     * @return an {@link Optional} containing a list of {@link FileObject} instances that satisfy the condition if the file is read successfully, or an empty {@link Optional} otherwise.
     */
    @Override
    public Optional<List<? extends FileObject>> readFile(String pathValue, Predicate<? super FileObject> condition) {
        return readFile(pathValue, 1, condition);
    }

    /**
     * Constructs an {@link OmiZone} object from a line of text by splitting the line at each semicolon and mapping
     * the resulting elements to the fields of an {@link OmiZone} object using the {@link OmiZone.OmiZoneBuilder}.
     *
     * @param line a line of text from the file.
     * @return a constructed {@link OmiZone} object.
     */
    @Override
    protected FileObject constructObject(String line) {
        String[] elements = line.split(";");
        /*
         * Example line elements:
         * Area_territoriale;Regione;Prov;Comune_ISTAT;Comune_cat;Sez;Comune_amm;Comune_descrizione;Fascia;Zona_Descr;
         * Zona;LinkZona;Cod_tip_prev;Descr_tip_prev;Stato_prev;Microzona;
         */
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
}