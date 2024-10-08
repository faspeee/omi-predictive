package com.mercant.real.estate.core.fileoperation.implementation;

import com.mercant.real.estate.core.fileoperation.contract.FileObject;
import com.mercant.real.estate.core.fileoperation.contract.ReadFile;
import com.mercant.real.estate.core.model.genericmodel.OmiValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.mercant.real.estate.core.util.OperationUtil.createValuesFromLine;

/**
 * The {@code ReadFileValue} class extends {@link AbstractFile} and implements the {@link ReadFile} interface to provide
 * functionality for reading and parsing files containing {@link OmiValue} objects. This class specifically handles files
 * formatted with a specific structure, where each line represents an {@code OmiValue} object with fields separated by semicolons.
 * <p>
 * The class offers methods to:
 * <ul>
 *     <li>Read files from a specified path and convert them into lists of {@link FileObject} instances.</li>
 *     <li>Apply filtering conditions while reading the files.</li>
 *     <li>Construct {@link OmiValue} objects from individual lines of the file.</li>
 * </ul>
 * <p>
 * Example of a line in the file:
 * <pre>
 * Area_territoriale;Regione;Prov;Comune_ISTAT;Comune_cat;Sez;Comune_amm;Comune_descrizione;Fascia;Zona;LinkZona;
 * Cod_Tip;Descr_Tipologia;Stato;Stato_prev;Compr_min;Compr_max;Sup_NL_compr;Loc_min;Loc_max;Sup_NL_loc;
 * </pre>
 */
public final class ReadFileValue extends AbstractFile<FileObject> implements ReadFile {

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
    public Optional<List<FileObject>> readFile(String pathValue, Predicate<FileObject> condition) {
        return readFile(pathValue, 1, condition);
    }

    @Override
    public <K> Optional<Map<K, List<FileObject>>> readFile(String path, Predicate<Map.Entry<K, FileObject>> condition, Function<FileObject, K> function) {

        return readFile(path, 1, condition, function);
    }

    /**
     * Constructs an {@link OmiValue} object from a line of text by splitting the line at each semicolon and mapping
     * the resulting elements to the fields of an {@link OmiValue} object using the {@link OmiValue.ValueOmiBuilder}.
     *
     * @param line a line of text from the file.
     * @return a constructed {@link OmiValue} object.
     */
    @Override
    protected FileObject constructObject(String line) {
        String[] elements = line.split(";");
        /*
         * Example line elements:
         * Area_territoriale;Regione;Prov;Comune_ISTAT;Comune_cat;Sez;Comune_amm;Comune_descrizione;Fascia;Zona;LinkZona;
         * Cod_Tip;Descr_Tipologia;Stato;Stato_prev;Compr_min;Compr_max;Sup_NL_compr;Loc_min;Loc_max;Sup_NL_loc;
         */
        return createValuesFromLine(elements);
    }


}
