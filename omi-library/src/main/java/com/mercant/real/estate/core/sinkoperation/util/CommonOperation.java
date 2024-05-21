package com.mercant.real.estate.core.sinkoperation.util;

import com.mercant.real.estate.core.model.genericmodel.OmiValue;
import com.mercant.real.estate.core.model.genericmodel.OmiZone;
import com.mercant.real.estate.core.model.genericmodel.ValueAndZone;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonOperation {

    protected CommonOperation() {
    }

    /**
     * Groups OmiZone and OmiValue objects based on the zone link.
     *
     * @param omiZones     List of OmiZone objects.
     * @param omiValuesMap Map of OmiValue objects keyed by their zone link.
     * @return A map where the key is the zone link and the value is a list of ValueAndZone objects.
     */
    public static Map<String, List<ValueAndZone>> groupingValueAndZone(List<OmiZone> omiZones, Map<String, List<OmiValue>> omiValuesMap) {
        return omiZones.parallelStream()
                .flatMap(omiZone -> omiValuesMap.getOrDefault(omiZone.getLinkZona(), new ArrayList<>()).stream()
                        .map(omiValue -> new AbstractMap.SimpleImmutableEntry<>(omiZone.getLinkZona(), new ValueAndZone(omiZone, omiValue))))
                .collect(Collectors.groupingByConcurrent(AbstractMap.SimpleImmutableEntry::getKey,
                        Collectors.mapping(AbstractMap.SimpleImmutableEntry::getValue, Collectors.toList())));
    }
}
