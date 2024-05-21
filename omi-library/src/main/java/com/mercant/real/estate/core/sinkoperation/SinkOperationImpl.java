package com.mercant.real.estate.core.sinkoperation;

import com.mercant.real.estate.core.fileoperation.contract.FileObject;
import com.mercant.real.estate.core.fileoperation.contract.ReadFile;
import com.mercant.real.estate.core.fileoperation.implementation.ReadFileValue;
import com.mercant.real.estate.core.fileoperation.implementation.ReadFileZone;
import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.model.genericmodel.OmiZone;
import com.mercant.real.estate.core.model.genericmodel.ValueAndZone;
import com.mercant.real.estate.core.sinkoperation.util.CommonOperation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.mercant.real.estate.core.util.CollectionUtil.castListObject;

/**
 * SinkOperationImpl is a final class that implements the SinkOperation interface. This class provides functionality
 * to process, read, and group OmiZone and OmiValue objects from files. It leverages the ReadFile interface to read
 * files and uses various methods to filter and map data into required structures.
 * <p>
 * The class provides methods to:
 * <ul>
 *     <li>Read OmiValue and OmiZone objects from specified file paths.</li>
 *     <li>Group and map OmiValue and OmiZone objects.</li>
 *     <li>Process files with optional conditions on the read objects.</li>
 * </ul>
 */
public final class SinkOperationImpl extends CommonOperation implements SinkOperation {

    // Instances of ReadFile for reading value and zone files.
    private final ReadFile readFileValue = new ReadFileValue();
    private final ReadFile readFileZone = new ReadFileZone();


    /**
     * Casts a list of FileObject to a list of OmiZone objects.
     *
     * @param fileObjectList List of FileObject.
     * @return A list of OmiZone objects.
     */
    private static List<OmiZone> castFileObjectToOmiZone(List<FileObject> fileObjectList) {
        return fileObjectList.stream()
                .filter(OmiZone.class::isInstance)
                .map(OmiZone.class::cast)
                .toList();
    }

    /**
     * Groups OmiValue objects based on their zone link.
     *
     * @param fileObjectList List of OmiValue objects.
     * @return A map where the key is the zone link and the value is a list of OmiValue objects.
     */
    private static Map<String, List<OmiValue>> groupingValue(List<OmiValue> fileObjectList) {
        return fileObjectList.stream()
                .filter(Objects::nonNull)
                .map(OmiValue.class::cast)
                .map(omiValue -> new AbstractMap.SimpleImmutableEntry<>(omiValue.getLinkZona(), omiValue))
                .collect(Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey, Collectors.mapping(AbstractMap.SimpleImmutableEntry::getValue, Collectors.toList())));
    }

    /**
     * Groups OmiZone and OmiValue objects and logs the execution time.
     *
     * @param supplier Supplier that provides the grouping result.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    private static Map<String, List<ValueAndZone>> groupingValueAndZone(Supplier<Map<String, List<ValueAndZone>>> supplier) {
        long initTimeTest = System.currentTimeMillis();
        Map<String, List<ValueAndZone>> valueAndZoneTest = supplier.get();
        long endTimeTest = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter.format((endTimeTest - initTimeTest) / 1000d) + " seconds" + "dimension map is " + valueAndZoneTest.values().stream().mapToLong(Collection::size).sum());
        return valueAndZoneTest;
    }

    /**
     * Reads OmiValue objects from the specified file path.
     *
     * @param pathValue The path to the value file.
     * @return An Optional containing a list of FileObject if the file is read successfully, or an empty Optional otherwise.
     */
    private Optional<List<FileObject>> readFileValue(String pathValue) {
        return readFileValue.readFile(pathValue);
    }

    /**
     * Reads OmiZone objects from the specified file path.
     *
     * @param pathZone The path to the zone file.
     * @return An Optional containing a list of FileObject if the file is read successfully, or an empty Optional otherwise.
     */
    private Optional<List<FileObject>> readFileZone(String pathZone) {
        return readFileZone.readFile(pathZone);
    }


    @Override
    public Map<String, List<ValueAndZone>> processAllFiles(String pathValue, String pathZone) {
        System.out.println("read the next files " + pathValue + " + " + pathZone);
        Map<String, List<OmiValue>> omiValuesMap = groupingValue(castListObject(readFileValue(pathValue).orElseGet(ArrayList::new), OmiValue.class::cast));
        return groupingValueAndZone(pathZone, omiValuesMap);
    }

    /**
     * Groups OmiZone and OmiValue objects from the provided paths.
     *
     * @param pathZone     The path to the zone file.
     * @param omiValuesMap Map of OmiValue objects keyed by their zone link.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    private Map<String, List<ValueAndZone>> groupingValueAndZone(String pathZone, Map<String, List<OmiValue>> omiValuesMap) {
        List<OmiZone> omiZones = castFileObjectToOmiZone(readFileZone(pathZone).orElseGet(ArrayList::new));
        return groupingValueAndZone(() -> groupingValueAndZone(omiZones, omiValuesMap));
    }


    @Override
    public List<OmiValue> readAllValue(String pathValue) {
        return castListObject(readFileValue.readFile(pathValue).orElseGet(ArrayList::new), OmiValue.class::cast);
    }


    @Override
    public List<OmiZone> readAllZone(String pathZone) {
        return castListObject(readFileZone.readFile(pathZone).orElseGet(ArrayList::new), OmiZone.class::cast);
    }


    @Override
    public List<OmiValue> readValueWithCondition(String pathValue, Predicate<FileObject> condition) {
        return readValueWithConditionSupport(pathValue, condition);
    }

    /**
     * Reads OmiValue objects from the specified file path that match the given condition.
     *
     * @param pathValue The path to the value file.
     * @param condition The condition to filter the OmiValue objects.
     * @return A list of OmiValue objects that match the condition.
     */
    private List<OmiValue> readValueWithConditionSupport(String pathValue, Predicate<FileObject> condition) {
        return castListObject(readFileValue.readFile(pathValue, condition).orElseGet(ArrayList::new), OmiValue.class::cast);
    }


    @Override
    public List<OmiZone> readZoneWithCondition(String pathZone, Predicate<FileObject> condition) {
        return readZoneWithConditionSupport(pathZone, condition);
    }

    /**
     * Reads OmiZone objects from the specified file path that match the given condition.
     *
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiZone objects.
     * @return A list of OmiZone objects that match the condition.
     */
    private List<OmiZone> readZoneWithConditionSupport(String pathZone, Predicate<FileObject> condition) {
        return castListObject(readFileZone.readFile(pathZone, condition).orElseGet(ArrayList::new), OmiZone.class::cast);
    }


    @Override
    public Map<String, List<ValueAndZone>> processFilesWithConditionValue(String pathValue, String pathZone, Predicate<FileObject> condition) {
        System.out.println("read the next files " + pathValue + " + " + pathZone);
        Map<String, List<OmiValue>> omiValuesMap = groupingValue(readValueWithConditionSupport(pathValue, condition));
        return groupingValueAndZone(pathZone, omiValuesMap);
    }


    @Override
    public Map<String, List<ValueAndZone>> processFilesWithConditionZone(String pathValue, String pathZone, Predicate<FileObject> condition) {
        Map<String, List<OmiValue>> omiValuesMap = groupingValue(castListObject(readFileValue(pathValue).orElseGet(ArrayList::new), OmiValue.class::cast));
        List<OmiZone> omiZones = readZoneWithConditionSupport(pathZone, condition);
        return groupingValueAndZone(omiZones, omiValuesMap);
    }


    @Override
    public Map<String, List<ValueAndZone>> processFilesWithConditionValueAndZone(String pathValue, String pathZone, Predicate<FileObject> valueCondition, Predicate<FileObject> condition) {
        Map<String, List<OmiValue>> omiValuesMap = groupingValue(readValueWithConditionSupport(pathValue, valueCondition));
        List<OmiZone> omiZones = readZoneWithConditionSupport(pathZone, condition);
        return groupingValueAndZone(omiZones, omiValuesMap);
    }

}
