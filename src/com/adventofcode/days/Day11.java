package com.adventofcode.days;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.adventofcode.days.utils.InputParser.parseFinalGridListToMap;
import static com.adventofcode.days.utils.InputParser.parseToList;
import static com.adventofcode.days.utils.Utils.getAdjacentPoints;
import static com.adventofcode.days.utils.Utils.printGrid;

public class Day11 {
    private static int X_MAX;
    private static int Y_MAX;
    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        var initialEnergyLevels = parseInput();
        if (DEBUG) {
            printGrid(initialEnergyLevels, X_MAX, Y_MAX);
        }
        System.out.println("Part 1 = " + part1(new HashMap<>(initialEnergyLevels)));
        System.out.println("Part 2 = " + part2(new HashMap<>(initialEnergyLevels)));
    }

    private static int part1(HashMap<Point, Integer> energyLevels) {
        int numFlashed = 0;
        for (int step = 0; step < 100; step++) {
            if (DEBUG) {
                System.out.println("Step " + (step + 1));
            }
            numFlashed += simulateAStep(energyLevels);
        }
        return numFlashed;
    }

    private static int part2(HashMap<Point, Integer> energyLevels) {
        int nowFlashing = 0;
        int step = 0;
        while (nowFlashing != 100) {
            step++;
            nowFlashing = simulateAStep(energyLevels);
        }
        return step;
    }

    private static int simulateAStep(HashMap<Point, Integer> currentEnergyLevels) {
        Set<Point> allPoints = new HashSet<>(currentEnergyLevels.keySet());
        Set<Point> hasFlashed = new HashSet<>();

        Set<Point> flashPoints = increaseEnergy(allPoints, currentEnergyLevels, hasFlashed);

        // time to flash
        while (!flashPoints.isEmpty()) {
            Set<Point> points = new HashSet<>(flashPoints);
            hasFlashed.addAll(points);
            flashPoints = new HashSet<>();
            for (Point point : points) {
                var adjacentPoints = new HashSet<>(getAdjacentPoints(point, X_MAX, Y_MAX, true));
                flashPoints.addAll(increaseEnergy(adjacentPoints, currentEnergyLevels, hasFlashed));
            }
        }

        // reduce flashed energy to 0
        for (Point point : hasFlashed) {
            currentEnergyLevels.put(point, 0);
        }
        if (DEBUG) {
            printGrid(currentEnergyLevels, X_MAX, Y_MAX);
        }
        return hasFlashed.size();
    }

    private static Set<Point> increaseEnergy(Set<Point> points, HashMap<Point, Integer> currentEnergyLevels, Set<Point> hasFlashed) {
        Set<Point> flashPoints = new HashSet<>();

        // increase energy level by 1
        for (Point point : points) {
            currentEnergyLevels.put(point, currentEnergyLevels.get(point) + 1);
            if (currentEnergyLevels.get(point) > 9 && !hasFlashed.contains(point)) {
                flashPoints.add(point);
            }
        }
        return flashPoints;
    }

    private static HashMap<Point, Integer> parseInput() {
        var input = parseToList("day11.txt");
        X_MAX = input.get(0).length() - 1;
        Y_MAX = input.size() - 1;

        return parseFinalGridListToMap(input, "");
    }
}
