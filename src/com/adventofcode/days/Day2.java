package com.adventofcode.days;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputParser.readInput;

public class Day2 {

    public static void main(String[] args) {
        var course = parseInput();
        System.out.println("Part 1 = " + part1(course));
        System.out.println("Part 2 = " + part2(course));
    }

    private static int part1(List<Map.Entry<String, Integer>> course) {
        int horizontal = 0;
        int vertical = 0;

        for (Map.Entry<String, Integer> entry : course) {
            var coordinates = dir(entry);
            assert coordinates != null;
            horizontal += coordinates.getKey();
            vertical += coordinates.getValue();
        }
        return horizontal * vertical;
    }

    private static int part2(List<Map.Entry<String, Integer>> course) {
        int horizontal = 0;
        int vertical = 0;
        int aim = 0;

        for (Map.Entry<String, Integer> entry : course) {
            var coordinates = dirWithAim(entry, aim);
            assert coordinates != null;
            horizontal += coordinates.get(0);
            vertical += coordinates.get(1);
            aim += coordinates.get(2);
        }
        return horizontal * vertical;
    }

    private static Map.Entry<Integer, Integer> dir(Map.Entry<String, Integer> dir) {
        // return value horizontal, vertical
        switch (dir.getKey()) {
            case "forward":
                return Map.entry(dir.getValue(), 0);
            case "up":
                return Map.entry(0, -dir.getValue());
            case "down":
                return Map.entry(0, dir.getValue());
            default:
                System.out.println("Something went wrong");
                return null;
        }
    }

    private static List<Integer> dirWithAim(Map.Entry<String, Integer> dir, Integer currentAim) {
        // return value horizontal, vertical, aim
        switch (dir.getKey()) {
            case "forward":
                return List.of(dir.getValue(), currentAim * dir.getValue(), 0);
            case "up":
                return List.of(0, 0, -dir.getValue());
            case "down":
                return List.of(0, 0, dir.getValue());
            default:
                System.out.println("Something went wrong");
                return null;
        }

    }

    private static List<Map.Entry<String, Integer>> parseInput() {
        var in = readInput("day2.txt").collect(Collectors.toList());
        List<Map.Entry<String, Integer>> course = new ArrayList<>();
        for (String s : in) {
            var split = s.split(" ");
            course.add(Map.entry(split[0], Integer.parseInt(split[1])));
        }
        return course;
    }

}
