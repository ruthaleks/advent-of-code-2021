package com.adventofcode.days;

import java.util.stream.Stream;

import static com.adventofcode.days.utils.InputReader.readInput;

public class Day1 {

    public static void main(String[] args) {
        Stream<String> in = readInput("day1.txt");
        in.forEach(System.out::println);
    }
}
