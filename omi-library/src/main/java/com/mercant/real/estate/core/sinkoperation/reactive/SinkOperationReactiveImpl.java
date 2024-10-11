package com.mercant.real.estate.core.sinkoperation.reactive;

import com.mercant.real.estate.core.fileoperation.contract.FileObject;
import com.mercant.real.estate.core.fileoperation.reactive.contract.ReadFileReactive;
import com.mercant.real.estate.core.fileoperation.reactive.implementation.ReadFileValue;
import com.mercant.real.estate.core.fileoperation.reactive.implementation.ReadFileZone;
import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.model.genericmodel.OmiZone;
import com.mercant.real.estate.core.model.genericmodel.ValueAndZone;
import com.mercant.real.estate.core.sinkoperation.util.CommonOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.mercant.real.estate.core.util.CollectionUtil.castMultiObject;

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
public final class SinkOperationReactiveImpl extends CommonOperation implements SinkOperationReactive {

    // Instances of ReadFile for reading value and zone files.
    private final ReadFileReactive readFileValue = new ReadFileValue();
    private final ReadFileReactive readFileZone = new ReadFileZone();


    /**
     * Casts a list of FileObject to a list of OmiZone objects.
     *
     * @param fileObjectList List of FileObject.
     * @return A list of OmiZone objects.
     */
    private static Multi<OmiZone> castFileObjectToOmiZone(Multi<FileObject> fileObjectList) {
        return fileObjectList
                .filter(OmiZone.class::isInstance)
                .map(OmiZone.class::cast);
    }

    /**
     * Groups OmiValue objects based on their zone link.
     *
     * @param fileObjectList List of OmiValue objects.
     * @return A map where the key is the zone link and the value is a list of OmiValue objects.
     */
    private static Multi<Map<String, List<OmiValue>>> groupingValue(Multi<Optional<OmiValue>> fileObjectList) {
        return fileObjectList.collect()
                .asMultiMap(omiValue -> omiValue.map(OmiValue::getLinkZona).orElse(""))
                .map(map -> map.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, y -> y.getValue().stream()
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .toList())))
                .toMulti();
    }

    /**
     * Groups OmiZone and OmiValue objects and logs the execution time.
     *
     * @param supplier Supplier that provides the grouping result.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    private static Multi<Map.Entry<String, List<ValueAndZone>>> groupingValueAndZone(Supplier<Map<String, List<ValueAndZone>>> supplier) {
        long initTimeTest = System.currentTimeMillis();
        Map<String, List<ValueAndZone>> valueAndZoneTest = supplier.get();
        long endTimeTest = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter.format((endTimeTest - initTimeTest) / 1000d) + " seconds" + "dimension map is " + valueAndZoneTest.values().stream().mapToLong(List::size).sum());
        return Multi.createFrom().iterable(valueAndZoneTest.entrySet());
    }

    private static List<OmiZone> removeOptional(List<Optional<OmiZone>> optionalList) {
        return optionalList.stream().filter(Optional::isPresent).map(Optional::get).toList();
    }

    /**
     * Reads OmiValue objects from the specified file path.
     *
     * @param pathValue The path to the value file.
     * @return An Optional containing a list of FileObject if the file is read successfully, or an empty Optional otherwise.
     */
    private Multi<Optional<FileObject>> readFileValue(String pathValue) {
        return readFileValue.readFile(pathValue);
    }

    /**
     * Reads OmiZone objects from the specified file path.
     *
     * @param pathZone The path to the zone file.
     * @return An Optional containing a list of FileObject if the file is read successfully, or an empty Optional otherwise.
     */
    private Multi<Optional<FileObject>> readFileZone(String pathZone) {
        return readFileZone.readFile(pathZone);
    }

    @Override
    public Multi<Map.Entry<String, List<ValueAndZone>>> processAllFiles(String pathValue, String pathZone) {
        System.out.println("read the next files " + pathValue + " + " + pathZone);
        return groupingValue(castMultiObject(readFileValue(pathValue), OmiValue.class::cast))
                .flatMap(omiValuesMap -> groupingValueAndZone(pathZone, omiValuesMap));
    }

    @Override
    public Multi<Map.Entry<String, List<ValueAndZone>>> processFilesWithConditionValue(String pathValue, String pathZone, Predicate<FileObject> condition) {
        System.out.println("read the next files " + pathValue + " + " + pathZone);
        return groupingValue(readValueWithConditionSupport(pathValue, condition))
                .flatMap(omiValuesMap -> groupingValueAndZone(pathZone, omiValuesMap));
    }

    /**
     * Groups OmiZone and OmiValue objects from the provided paths.
     *
     * @param pathZone     The path to the zone file.
     * @param omiValuesMap Map of OmiValue objects keyed by their zone link.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    private Multi<Map.Entry<String, List<ValueAndZone>>> groupingValueAndZone(String pathZone, Map<String, List<OmiValue>> omiValuesMap) {
        return castFileObjectToOmiZone(readFileZone(pathZone).filter(Optional::isPresent).map(Optional::get)).collect().asList()
                .map(omiZones -> groupingValueAndZone(() -> groupingValueAndZone(omiZones, omiValuesMap)))
                .toMulti().flatMap(multi -> multi);
    }

    @Override
    public Multi<Optional<OmiValue>> readAllValue(String pathValue) {
        return castMultiObject(readFileValue.readFile(pathValue), OmiValue.class::cast);
    }

    @Override
    public Multi<Optional<OmiZone>> readAllZone(String pathZone) {
        return castMultiObject(readFileZone.readFile(pathZone), OmiZone.class::cast);
    }

    @Override
    public Multi<Optional<OmiValue>> readValueWithCondition(String pathValue, Predicate<FileObject> condition) {
        return readValueWithConditionSupport(pathValue, condition);
    }

    /**
     * Reads OmiValue objects from the specified file path that match the given condition.
     *
     * @param pathValue The path to the value file.
     * @param condition The condition to filter the OmiValue objects.
     * @return A list of OmiValue objects that match the condition.
     */
    private Multi<Optional<OmiValue>> readValueWithConditionSupport(String pathValue, Predicate<FileObject> condition) {
        return castMultiObject(readFileValue.readFile(pathValue, condition), OmiValue.class::cast);
    }

    @Override
    public Multi<Optional<OmiZone>> readZoneWithCondition(String pathZone, Predicate<FileObject> condition) {
        return readZoneWithConditionSupport(pathZone, condition);
    }

    /**
     * Reads OmiZone objects from the specified file path that match the given condition.
     *
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiZone objects.
     * @return A list of OmiZone objects that match the condition.
     */
    private Multi<Optional<OmiZone>> readZoneWithConditionSupport(String pathZone, Predicate<FileObject> condition) {
        return castMultiObject(readFileZone.readFile(pathZone, condition), OmiZone.class::cast);
    }


    @Override
    public Multi<Map.Entry<String, List<ValueAndZone>>> processFilesWithConditionZone(String pathValue, String pathZone, Predicate<FileObject> condition) {
        Uni<Map<String, List<OmiValue>>> omiValuesMap = groupingValue(castMultiObject(readFileValue(pathValue), OmiValue.class::cast)).toUni();
        return processZonesAndGrouping(pathZone, condition, omiValuesMap);
    }

    private Multi<Map.Entry<String, List<ValueAndZone>>> processZonesAndGrouping(String pathZone, Predicate<FileObject> condition, Uni<Map<String, List<OmiValue>>> omiValuesMap) {
        Uni<List<OmiZone>> omiZones = readZoneWithConditionSupport(pathZone, condition).collect().asList().map(SinkOperationReactiveImpl::removeOptional);
        return Uni.combine().all().unis(omiZones, omiValuesMap).asTuple()
                .map(tuple -> groupingValueAndZone(tuple.getItem1(), tuple.getItem2()))
                .toMulti()
                .flatMap(map -> Multi.createFrom().iterable(map.entrySet()));
    }

    @Override
    public Multi<Map.Entry<String, List<ValueAndZone>>> processFilesWithConditionValueAndZone(String pathValue, String pathZone, Predicate<FileObject> valueCondition, Predicate<FileObject> condition) {
        Uni<Map<String, List<OmiValue>>> omiValuesMap = groupingValue(readValueWithConditionSupport(pathValue, valueCondition)).toUni();
        return processZonesAndGrouping(pathZone, condition, omiValuesMap);

    }

}
