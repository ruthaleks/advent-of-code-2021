package com.adventofcode.days.utils;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputParser {
    private final static String INPUT_FILE_ROOT_PATH = "src/com/adventofcode/input/";

    public static Stream<String> readInput(String fileName) {
        try {
            return Files.lines(Paths.get(INPUT_FILE_ROOT_PATH + fileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Stream.empty();
        }
    }

    public static List<String> parseToList(String file) {
        return readInput(file).collect(Collectors.toList());
    }

    public static HashMap<Point, Integer> parseFinalGridListToMap(List<String> input, String delimiter) {
        HashMap<Point, Integer> grid = new HashMap<>();
        int x_max = input.get(0).length() - 1;
        int y_max = input.size() - 1;
        for (int y = 0; y <= y_max; y++) {
            var row = input.get(y).split(delimiter);
            for (int x = 0; x <= x_max; x++) {
                grid.put(new Point(x, y), Integer.parseInt(row[x]));
            }
        }
        return grid;
    }
}
