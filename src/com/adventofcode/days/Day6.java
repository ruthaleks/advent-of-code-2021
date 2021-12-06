package com.adventofcode.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputReader.readInput;

public class Day6 {
    private static List<Integer> input;

    public static void main(String[] args) {
        parseInput();
        System.out.println("Part 1 = " + calcFish(80));
        System.out.println("Part 2 = " + calcFish(256));
    }

    private static long calcFish(int days) {
        // timer, count
        HashMap<Integer, Long> container = new HashMap<>();

        // add input
        for (int in : input) {
            container.put(in, container.containsKey(in) ? container.get(in) + 1 : 1);
        }

        for (int day = 0; day < days; day++) {
            HashMap<Integer, Long> newContainer = new HashMap<>();
            var allFish = new HashSet<>(container.keySet());
            for (Integer fish : allFish) {
                long number = container.get(fish);
                if (fish == 0) {
                    newContainer.put(8, newContainer.containsKey(8) ? newContainer.get(8) + number : number);
                    newContainer.put(6, newContainer.containsKey(6) ? newContainer.get(6) + number : number);
                } else {
                    newContainer.put(fish - 1, newContainer.containsKey(fish - 1) ? newContainer.get(fish - 1) + number : number);
                }
            }
            container = newContainer;
        }
        return container.values().stream().mapToLong(Long::longValue).sum();
    }


    private static void parseInput() {
        var in = readInput("day6.txt").collect(Collectors.toList());
        input = Arrays.stream(in.get(0).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

    }
}

