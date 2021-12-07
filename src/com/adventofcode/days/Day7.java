package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputReader.readInput;

public class Day7 {
    private static List<Integer> input;
    private static Set<Integer> positions;

    public static void main(String[] args) {
        parseInput();
        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());
    }

    private static int part1() {
        int minFuel = Integer.MAX_VALUE;
        for (int pos : positions) {
            int fuel = calFuel(pos);
            if (minFuel > fuel) {
                minFuel = fuel;
            }
        }
        return minFuel;
    }

    private static int part2() {
        int minFuel = Integer.MAX_VALUE;

        for (int p : positions) {
            int fuel = calFuelInc(p);
            if (minFuel > fuel) {
                minFuel = fuel;
            }
        }
        return minFuel;
    }

    private static int calFuel(int position) {
        int sum = 0;
        for (int i : input) {
            sum += Math.abs(i - position);
        }
        return sum;
    }

    private static int calFuelInc(int position) {
        int sum = 0;
        for (int i : input) {
            int pos = Math.abs(i - position);
            for (int j = 1; j < (pos+1); j++) {
                sum += j;
            }
        }
        return sum;
    }



    private static void parseInput() {
        var in = readInput("day7.txt").collect(Collectors.toList());
        input = Arrays.stream(in.get(0).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        positions = new HashSet<>(input);
    }
}
