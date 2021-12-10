package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputReader.readInput;

public class Day10 {
    private static List<String> input;
    private static Set<String> startChars;
    private static Set<String> endChars;
    private static Map<String, String> delimiterMap;

    public static void main(String[] args) {
        parseInput();
        init();

        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());
       // part2();
        //System.out.println(input);
    }

    private static long part2() {

        /*
        ): 1 point.
]: 2 points.
}: 3 points.
>: 4 points.
         */

        Map<String, Integer> cost = Map.of(")", 1, "]", 2, "}", 3, ">", 4);
        List<Long> totalScores = new ArrayList<>();
        int res = 0;
        for (String row : input) {
            var line = Arrays.stream(row.split("")).collect(Collectors.toList());
            //System.out.println(line);
            var list = complete(null, line, 0, new ArrayList<>());
            if (list != null) {
                //System.out.println(line);
                //System.out.println(list);
                long totalScore = 0;
                for( int i = list.size() - 1; i >= 0 ; i--) {
                    //System.out.println(delimiterMap.get(list.get(i)));
                    totalScore *= 5;
                    totalScore += cost.get(delimiterMap.get(list.get(i)));
                    System.out.println(totalScore);
                }
                System.out.println(totalScore);
                totalScores.add(totalScore);

                //res += cost.get(line.get(idx));
            }
            // System.out.println("idx = " + idx);
            // System.out.println(line);
            // System.out.println("Incorrect delimiter = " + res);
            // System.out.println("------------");
        }

        var sorted = totalScores.stream().sorted().collect(Collectors.toList());
        return sorted.get(totalScores.size()/2);
    }

    private static List<String> complete(String start, List<String> line, int idx, List<String> prevStarts) {
        if (idx == line.size()) {
            prevStarts.add(start);
            return prevStarts;
        }
        String next = line.get(idx);
       // System.out.println("start = " + start + " next = " + next + " idx " + idx + " prevStart = " + prevStarts);
        if (startChars.contains(next)) {
            if (start != null) {
                prevStarts.add(start);
            }
            return complete(next, line, idx + 1, prevStarts);
        }

        if (endChars.contains(next)) {
            // System.out.println("start = " + start);
            if (!delimiterMap.containsKey(start)) {
                return complete(start, line, idx + 1, prevStarts);
            }
            if (delimiterMap.get(start).equals(next)) {
                if (prevStarts.isEmpty()) {
                    return null;
                }
                String prevStart = prevStarts.get(prevStarts.size() - 1);
                prevStarts.remove(prevStarts.size() - 1);
                return complete(prevStart, line, idx + 1, prevStarts);
            } else {
               // System.out.println("Expected " + delimiterMap.get(start) + " but found " + next + " instead");
                return null;
            }
        }

        System.out.println("Something went wrong");
        return null;
    }

    private static int part1() {

        /*
        ): 3 points.
]: 57 points.
}: 1197 points.
>: 25137 points.
         */

        Map<String, Integer> cost = Map.of(")", 3, "]", 57, "}", 1197, ">", 25137);

        int res = 0;
        for (String row : input) {
            var line = Arrays.stream(row.split("")).collect(Collectors.toList());
            //System.out.println(line);
            var idx = findMatchingDelimiter(null, line, 0, new ArrayList<>());
            if (idx >= 0) {
                res += cost.get(line.get(idx));
            }
           // System.out.println("idx = " + idx);
           // System.out.println(line);
           // System.out.println("Incorrect delimiter = " + res);
           // System.out.println("------------");
        }
        return res;
    }

    private static int findMatchingDelimiter(String start, List<String> line, int idx, List<String> prevStarts) {
        if (idx == line.size()) {
            return -1;
        }
        String next = line.get(idx);
        //System.out.println("start = " + start + " next = " + next + " idx " + idx + " prevStart = " + prevStarts);
        if (startChars.contains(next)) {
            if (start != null) {
                prevStarts.add(start);
            }
            return findMatchingDelimiter(next, line, idx + 1, prevStarts);
        }

        if (endChars.contains(next)) {
           // System.out.println("start = " + start);
            if (!delimiterMap.containsKey(start)) {
                return findMatchingDelimiter(start, line, idx + 1, prevStarts);
            }
            if (delimiterMap.get(start).equals(next)) {
                if (prevStarts.isEmpty()) {
                    return -1;
                }
                String prevStart = prevStarts.get(prevStarts.size() - 1);
                prevStarts.remove(prevStarts.size() - 1);
                return findMatchingDelimiter(prevStart, line, idx + 1, prevStarts);
            } else {
                System.out.println("Expected " + delimiterMap.get(start) + " but found " + next + " instead");
                return idx;
            }
        }

        System.out.println("Something went wrong");
        return -3;
    }

    private static void init() {
        startChars = new HashSet<>(Arrays.asList("(", "{", "[", "<"));
        endChars = new HashSet<>(Arrays.asList(")", "}", "]", ">"));
        delimiterMap = Map.of("(", ")", "{", "}", "[", "]", "<", ">");
    }

    private static void parseInput() {
        input = readInput("day10.txt").collect(Collectors.toList());
    }
}
