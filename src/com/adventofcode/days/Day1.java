package com.adventofcode.days;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adventofcode.days.utils.InputParser.readInput;

public class Day1 {

    public static void main(String[] args) {
        Stream<String> in = readInput("day1.txt");
        List<Integer> data = in.map(Integer::parseInt).collect(Collectors.toList());
        System.out.println("Part 1 = " + part1(data));
        System.out.println("Part 2 = " + part2(data));
    }

    private static int part1(List<Integer> data) {
        int count = 0;
        for (int i = 0; i < (data.size() - 1); i++) {
            count = data.get(i + 1) > data.get(i) ? count + 1 : count;
        }
        return count;
    }

    private static int part2(List<Integer> data) {
        int count = 0;
        for (int i = 0; i < (data.size() - 1); i++) {
            count = compare(data, i) ? count + 1 : count;
        }
        return count;
    }

    private static boolean compare(List<Integer> data, int idx) {
        return slidingWindow(data, idx + 1) > slidingWindow(data, idx);
    }

    private static int slidingWindow(List<Integer> data, int idx) {
        int sum = 0;
        int maxIdx = Math.min(idx + 3, data.size());
        for (int i = idx; i < maxIdx; i++) {
            sum += data.get(i);
        }
        return sum;
    }
}
