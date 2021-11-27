package com.adventofcode.days.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class InputReader {
    private final static String INPUT_FILE_ROOT_PATH = "src/com/adventofcode/input/";

    public static Stream<String> readInput(String fileName) {
        try {
            return Files.lines(Paths.get(INPUT_FILE_ROOT_PATH + fileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return Stream.empty();
        }
    }
}
