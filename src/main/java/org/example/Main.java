package org.example;

import org.example.omi.core.sinkoperation.SinkOperationImpl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        File file = new File("src/main/resources/");
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