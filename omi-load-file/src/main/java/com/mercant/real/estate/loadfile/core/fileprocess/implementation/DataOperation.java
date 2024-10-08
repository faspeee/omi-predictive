package com.mercant.real.estate.loadfile.core.fileprocess.implementation;

import com.mercant.real.estate.core.fileoperation.contract.FileObject;
import com.mercant.real.estate.core.fileoperation.contract.ReadFile;
import com.mercant.real.estate.core.fileoperation.implementation.ReadFileValue;
import com.mercant.real.estate.core.fileoperation.implementation.ReadFileZone;
import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.model.genericmodel.OmiZone;
import com.mercant.real.estate.loadfile.core.fileprocess.contract.Operation;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataOperation implements Operation {
    private final ReadFile readValueFile = new ReadFileValue();
    private final ReadFile readZoneFile = new ReadFileZone();
    String valuePath = "C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_VALORI.csv";
    String zonePath = "C:\\Program1\\project-fabian\\omi-predictive\\omi-library\\src\\main\\resources\\QI20051\\QI_1027287_1_20051_ZONE.csv";

    @Override
    public <K, V> Map<K, List<V>> readOperation() {
        Map<String, List<FileObject>> valueByLinkZone = readValueFile.readFile(valuePath, entry -> entry.getValue() != null, fileObject -> {
            if (fileObject instanceof OmiValue omiValue) {
                return omiValue.getComuneCat();
            } else {
                return "";
            }
        }).orElseGet(HashMap::new);
        Map<String, List<FileObject>> zoneByLinkZone = readZoneFile.readFile(zonePath, entry -> entry.getValue() != null, fileObject -> {
            if (fileObject instanceof OmiZone omiZone) {
                return omiZone.getComuneCat();
            } else {
                return "";
            }
        }).orElseGet(HashMap::new);

        valueByLinkZone.entrySet().stream()
                .flatMap(entryValue -> entryValue.getValue().stream()
                        .map(value -> new AbstractMap.SimpleImmutableEntry<>(entryValue.getKey(), "")));

        System.out.println("");
        return new HashMap<>();
    }
}
