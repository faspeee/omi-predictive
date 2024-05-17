package org.example.omi.core.sinkoperation;

import org.example.omi.core.fileoperation.FileObject;
import org.example.omi.core.model.ValueAndZone;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface SinkOperation {
    Map<String, List<ValueAndZone>> processAllFiles(String pathValue, String pathZone);

    <T extends FileObject> Map<String, List<ValueAndZone>> processAllFiles(String pathValue, String pathZone, Predicate<T> omiZonePredicate);
}
