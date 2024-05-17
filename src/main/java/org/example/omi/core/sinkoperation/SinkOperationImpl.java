package org.example.omi.core.sinkoperation;

import org.example.omi.core.fileoperation.FileObject;
import org.example.omi.core.fileoperation.ReadFileValue;
import org.example.omi.core.fileoperation.ReadFileZone;
import org.example.omi.core.model.OmiValue;
import org.example.omi.core.model.OmiZone;
import org.example.omi.core.model.ValueAndZone;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SinkOperationImpl implements SinkOperation {

    private Map<String, List<OmiValue>> groupingValue(List<FileObject> fileObjectList) {
        return fileObjectList.stream()
                .filter(OmiValue.class::isInstance)
                .map(OmiValue.class::cast)
                .map(omiValue -> new AbstractMap.SimpleImmutableEntry<>(omiValue.getLinkZona(), omiValue))
                .collect(Collectors.groupingBy(AbstractMap.SimpleImmutableEntry::getKey, Collectors.mapping(AbstractMap.SimpleImmutableEntry::getValue, Collectors.toList())));
    }

    private Optional<List<FileObject>> readFileValue(String pathValue) {
        return new ReadFileValue().readFile(pathValue);
    }

    private List<OmiZone> castFileObjectToOmiZone(List<FileObject> fileObjectList) {
        return fileObjectList.stream()
                .filter(OmiZone.class::isInstance)
                .map(OmiZone.class::cast)
                .toList();
    }

    private Optional<List<FileObject>> readFileZone(String pathZone) {
        return new ReadFileZone().readFile(pathZone);
    }

    private Map<String, List<ValueAndZone>> groupingValueAndZone(List<OmiZone> omiZones, Map<String, List<OmiValue>> omiValuesMap) {
        return omiZones.parallelStream()
                .flatMap(omiZone -> omiValuesMap.getOrDefault(omiZone.getLinkZona(), new ArrayList<>()).stream()
                        .map(omiValue -> new AbstractMap.SimpleImmutableEntry<>(omiZone.getLinkZona(), new ValueAndZone(omiZone, omiValue))))
                .collect(Collectors.groupingByConcurrent(AbstractMap.SimpleImmutableEntry::getKey,
                        Collectors.mapping(AbstractMap.SimpleImmutableEntry::getValue, Collectors.toList())));
    }

    @Override
    public Map<String, List<ValueAndZone>> processAllFiles(String pathValue, String pathZone) {
        System.out.println("read the next files " + pathValue + " + " + pathZone);
        Map<String, List<OmiValue>> omiValuesMap = groupingValue(readFileValue(pathValue).orElseGet(ArrayList::new));
        List<OmiZone> omiZones = castFileObjectToOmiZone(readFileZone(pathZone).orElseGet(ArrayList::new));
        long initTimeTest = System.currentTimeMillis();
        Map<String, List<ValueAndZone>> valueAndZoneTest = groupingValueAndZone(omiZones, omiValuesMap);
        long endTimeTest = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter.format((endTimeTest - initTimeTest) / 1000d) + " seconds" + "dimension map is " + valueAndZoneTest.values().stream().mapToLong(Collection::size).sum());
        return valueAndZoneTest;
    }

    @Override
    public <T extends FileObject> Map<String, List<ValueAndZone>> processAllFiles(String pathValue, String pathZone, Predicate<T> omiZonePredicate) {
        return null;
    }
}
