package org.example.omi.core.sinkoperation;

import org.example.omi.core.fileoperation.contract.FileObject;
import org.example.omi.core.model.OmiValue;
import org.example.omi.core.model.OmiZone;
import org.example.omi.core.model.ValueAndZone;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface SinkOperation {
    /**
     * Processes all files and groups OmiZone and OmiValue objects based on the provided file paths.
     *
     * @param pathValue The path to the value file.
     * @param pathZone  The path to the zone file.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    default Map<String, List<ValueAndZone>> processAllFiles(String pathValue, String pathZone) {
        return Map.of();
    }

    /**
     * Reads all OmiValue objects from the specified file path.
     *
     * @param pathValue The path to the value file.
     * @return A list of OmiValue objects.
     */
    default List<OmiValue> readAllValue(String pathValue) {
        return List.of();
    }

    /**
     * Reads all OmiZone objects from the specified file path.
     *
     * @param pathZone The path to the zone file.
     * @return A list of OmiZone objects.
     */
    default List<OmiZone> readAllZone(String pathZone) {
        return List.of();
    }

    /**
     * Reads OmiValue objects from the specified file path that match the given condition.
     *
     * @param pathValue The path to the value file.
     * @param condition The condition to filter the OmiValue objects.
     * @return A list of OmiValue objects that match the condition.
     */
    default List<OmiValue> readValueWithCondition(String pathValue, Predicate<? super FileObject> condition) {
        return List.of();
    }

    /**
     * Reads OmiZone objects from the specified file path that match the given condition.
     *
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiZone objects.
     * @return A list of OmiZone objects that match the condition.
     */

    default List<OmiZone> readZoneWithCondition(String pathZone, Predicate<? super FileObject> condition) {
        return List.of();
    }

    /**
     * Processes files and groups OmiZone and OmiValue objects based on the provided file paths and value condition.
     *
     * @param pathValue The path to the value file.
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiValue objects.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    default Map<String, List<ValueAndZone>> processFilesWithConditionValue(String pathValue, String pathZone, Predicate<? super FileObject> condition) {
        return Map.of();
    }

    /**
     * Processes files and groups OmiZone and OmiValue objects based on the provided file paths and zone condition.
     *
     * @param pathValue The path to the value file.
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiZone objects.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    default Map<String, List<ValueAndZone>> processFilesWithConditionZone(String pathValue, String pathZone, Predicate<? super FileObject> condition) {
        return Map.of();
    }

    /**
     * Processes files and groups OmiZone and OmiValue objects based on the provided file paths and conditions.
     *
     * @param pathValue      The path to the value file.
     * @param pathZone       The path to the zone file.
     * @param valueCondition The condition to filter the OmiValue objects.
     * @param condition      The condition to filter the OmiZone objects.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    default Map<String, List<ValueAndZone>> processFilesWithConditionValueAndZone(String pathValue, String pathZone, Predicate<? super FileObject> valueCondition, Predicate<? super FileObject> condition) {
        return Map.of();
    }

}
