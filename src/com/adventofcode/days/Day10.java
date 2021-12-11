package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputParser.parseToList;

public class Day10 {
    private static List<String> input;
    private static Set<String> startChars;
    private static Set<String> endChars;
    private static Map<String, String> charPairs;

    public static void main(String[] args) {
        parseInput();
        init();

        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());
    }

    private static int part1() {
        Map<String, Integer> cost = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);

        int res = 0;
        for (String row : input) {
            var line = Arrays.stream(row.split("")).collect(Collectors.toList());
            var idx = findMissingChars(null, line, 0, new ArrayList<>());
            if (idx >= 0) {
                res += cost.get(line.get(idx));
            }
        }
        return res;
    }

    private static long part2() {
        Map<String, Integer> cost = Map.of(")", 1, "]", 2, "}", 3, ">", 4);
        List<Long> totalScores = new ArrayList<>();
        int res = 0;
        for (String row : input) {
            var line = Arrays.stream(row.split("")).collect(Collectors.toList());
            var list = completeLine(null, line, 0, new ArrayList<>());
            if (list != null) {
                long totalScore = 0;
                for (int i = list.size() - 1; i >= 0; i--) {
                    totalScore *= 5;
                    totalScore += cost.get(charPairs.get(list.get(i)));
                }
                totalScores.add(totalScore);
            }
        }

        var sorted = totalScores.stream().sorted().collect(Collectors.toList());
        return sorted.get(totalScores.size() / 2);
    }

    private static List<String> completeLine(String start, List<String> line, int idx, List<String> prevStarts) {
        if (idx == line.size()) {
            prevStarts.add(start);
            return prevStarts;
        }
        String next = line.get(idx);
        if (startChars.contains(next)) {
            if (start != null) {
                prevStarts.add(start);
            }
            return completeLine(next, line, idx + 1, prevStarts);
        }

        if (endChars.contains(next)) {
            if (!charPairs.containsKey(start)) {
                return completeLine(start, line, idx + 1, prevStarts);
            }
            if (charPairs.get(start).equals(next)) {
                if (prevStarts.isEmpty()) {
                    return null;
                }
                String prevStart = prevStarts.get(prevStarts.size() - 1);
                prevStarts.remove(prevStarts.size() - 1);
                return completeLine(prevStart, line, idx + 1, prevStarts);
            } else {
                return null;
            }
        }

        System.out.println("Something went wrong");
        return null;
    }


    private static int findMissingChars(String start, List<String> line, int idx, List<String> prevStarts) {
        if (idx == line.size()) {
            return -1;
        }
        String next = line.get(idx);
        if (startChars.contains(next)) {
            if (start != null) {
                prevStarts.add(start);
            }
            return findMissingChars(next, line, idx + 1, prevStarts);
        }

        if (endChars.contains(next)) {
            if (!charPairs.containsKey(start)) {
                return findMissingChars(start, line, idx + 1, prevStarts);
            }
            if (charPairs.get(start).equals(next)) {
                if (prevStarts.isEmpty()) {
                    return -1;
                }
                String prevStart = prevStarts.get(prevStarts.size() - 1);
                prevStarts.remove(prevStarts.size() - 1);
                return findMissingChars(prevStart, line, idx + 1, prevStarts);
            } else {
                System.out.println("Expected " + charPairs.get(start) + " but found " + next + " instead");
                return idx;
            }
        }
        System.out.println("Something went wrong");
        return -3;
    }

    private static void init() {
        startChars = new HashSet<>(Arrays.asList("(", "{", "[", "<"));
        endChars = new HashSet<>(Arrays.asList(")", "}", "]", ">"));
        charPairs = Map.of("(", ")", "{", "}", "[", "]", "<", ">");
    }

    private static void parseInput() {
        input = parseToList("day10.txt");
    }
}
