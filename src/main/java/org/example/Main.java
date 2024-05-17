package org.example;

import org.example.omi.core.model.OmiValue;
import org.example.omi.core.model.OmiZone;
import org.example.omi.core.model.ValueAndZone;
import org.example.omi.core.sinkoperation.SinkOperationImpl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        File file = new File("src/main/resources/");
        List<OmiValue> omiValues = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .parallel()
                .map(File::listFiles)
                .filter(files -> files != null && files.length == 2)
                .flatMap(files -> new SinkOperationImpl().readValueWithCondition(files[0].getAbsolutePath(), fileObject -> {
                    if (fileObject instanceof OmiValue omiValue) {
                        return omiValue.getProv().equals("TO");
                    } else {
                        return false;
                    }
                }).stream())
                .toList();

        System.out.println(omiValues);

        List<OmiZone> omiZones = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .parallel()
                .map(File::listFiles)
                .filter(files -> files != null && files.length == 2)
                .flatMap(files -> new SinkOperationImpl().readZoneWithCondition(files[0].getAbsolutePath(), fileObject -> {
                    if (fileObject instanceof OmiZone omiZone) {
                        return omiZone.getProv().equals("TO");
                    } else {
                        return false;
                    }
                }).stream())
                .toList();

        System.out.println(omiZones);
        List<Map<String, List<ValueAndZone>>> valueAndZoneFilterValue = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .parallel()
                .map(File::listFiles)
                .filter(files -> files != null && files.length == 2)
                .map(files -> new SinkOperationImpl().processFilesWithConditionValue(files[0].getAbsolutePath(), files[1].getAbsolutePath(), fileObject -> {
                    if (fileObject instanceof OmiValue omiValue) {
                        return omiValue.getProv().equals("TO");
                    } else {
                        return false;
                    }
                }))
                .toList();

        System.out.println(valueAndZoneFilterValue);
        List<Map<String, List<ValueAndZone>>> valueAndZoneFilterZone = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .parallel()
                .map(File::listFiles)
                .filter(files -> files != null && files.length == 2)
                .map(files -> new SinkOperationImpl().processFilesWithConditionZone(files[0].getAbsolutePath(), files[1].getAbsolutePath(), fileObject -> {
                    if (fileObject instanceof OmiZone omiZone) {
                        return omiZone.getProv().equals("TO");
                    } else {
                        return false;
                    }
                }))
                .toList();

        System.out.println(valueAndZoneFilterZone);
        List<Map<String, List<ValueAndZone>>> valueAndZoneFilterBelong = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .parallel()
                .map(File::listFiles)
                .filter(files -> files != null && files.length == 2)
                .map(files -> new SinkOperationImpl().processFilesWithConditionValueAndZone(files[0].getAbsolutePath(), files[1].getAbsolutePath(), fileObject -> {
                    if (fileObject instanceof OmiValue omiValue) {
                        return omiValue.getProv().equals("TO");
                    } else {
                        return false;
                    }
                }, fileObject2 -> {
                    if (fileObject2 instanceof OmiZone omiZone) {
                        return omiZone.getProv().equals("TO");
                    } else {
                        return false;
                    }
                }))
                .toList();

        System.out.println(valueAndZoneFilterBelong);

        List<Integer> list = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .parallel()
                .map(File::listFiles)
                .filter(files -> files != null && files.length == 2)
                .map(files -> new SinkOperationImpl().processAllFiles(files[0].getAbsolutePath(), files[1].getAbsolutePath()).values().stream().map(List::size)
                        .reduce(0, Integer::sum))
                .toList();
        System.out.println("" + list.stream().reduce(0, Integer::sum));

    }
}