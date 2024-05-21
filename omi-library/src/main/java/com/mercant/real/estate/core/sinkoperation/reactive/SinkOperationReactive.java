package com.mercant.real.estate.core.sinkoperation.reactive;

import com.mercant.real.estate.core.fileoperation.contract.FileObject;
import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.model.genericmodel.OmiZone;
import com.mercant.real.estate.core.model.genericmodel.ValueAndZone;
import io.smallrye.mutiny.Multi;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public interface SinkOperationReactive {
    /**
     * Processes all files and groups OmiZone and OmiValue objects based on the provided file paths.
     *
     * @param pathValue The path to the value file.
     * @param pathZone  The path to the zone file.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    default Multi<Map.Entry<String, List<ValueAndZone>>> processAllFiles(String pathValue, String pathZone) {
        return Multi.createFrom().empty();
    }

    /**
     * Reads all OmiValue objects from the specified file path.
     *
     * @param pathValue The path to the value file.
     * @return A list of OmiValue objects.
     */
    default Multi<Optional<OmiValue>> readAllValue(String pathValue) {
        return Multi.createFrom().empty();
    }

    /**
     * Reads all OmiZone objects from the specified file path.
     *
     * @param pathZone The path to the zone file.
     * @return A list of OmiZone objects.
     */
    default Multi<Optional<OmiZone>> readAllZone(String pathZone) {
        return Multi.createFrom().empty();
    }

    /**
     * Reads OmiValue objects from the specified file path that match the given condition.
     *
     * @param pathValue The path to the value file.
     * @param condition The condition to filter the OmiValue objects.
     * @return A list of OmiValue objects that match the condition.
     */
    default Multi<Optional<OmiValue>> readValueWithCondition(String pathValue, Predicate<FileObject> condition) {
        return Multi.createFrom().empty();
    }

    /**
     * Reads OmiZone objects from the specified file path that match the given condition.
     *
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiZone objects.
     * @return A list of OmiZone objects that match the condition.
     */

    default Multi<Optional<OmiZone>> readZoneWithCondition(String pathZone, Predicate<FileObject> condition) {
        return Multi.createFrom().empty();
    }

    /**
     * Processes files and groups OmiZone and OmiValue objects based on the provided file paths and value condition.
     *
     * @param pathValue The path to the value file.
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiValue objects.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    default Multi<Map.Entry<String, List<ValueAndZone>>> processFilesWithConditionValue(String pathValue, String pathZone, Predicate<FileObject> condition) {
        return Multi.createFrom().empty();
    }

    /**
     * Processes files and groups OmiZone and OmiValue objects based on the provided file paths and zone condition.
     *
     * @param pathValue The path to the value file.
     * @param pathZone  The path to the zone file.
     * @param condition The condition to filter the OmiZone objects.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    default Multi<Map.Entry<String, List<ValueAndZone>>> processFilesWithConditionZone(String pathValue, String pathZone, Predicate<FileObject> condition) {
        return Multi.createFrom().empty();
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
    default Multi<Map.Entry<String, List<ValueAndZone>>> processFilesWithConditionValueAndZone(String pathValue, String pathZone, Predicate<FileObject> valueCondition, Predicate<FileObject> condition) {
        return Multi.createFrom().empty();
    }


}
