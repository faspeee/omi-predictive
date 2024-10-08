package com.mercant.real.estate.loadfile.core.fileprocess.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Operation {
    default <K, V> Map<K, List<V>> readOperation() {
        return new HashMap<>();
    }
}
