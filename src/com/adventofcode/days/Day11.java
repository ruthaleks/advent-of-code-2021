package com.adventofcode.days;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputReader.readInput;

public class Day11 {
    private static final int X_MAX = 9;
    private static final int Y_MAX = 9;

    public static void main(String[] args) {
        HashMap<Point, Integer> energyLevels = parseInput();
        //printEnergyLevels(energyLevels);
        System.out.println("Part 1 = " + part1(energyLevels));

        energyLevels = parseInput();
        System.out.println("Part 2 = " + part2(energyLevels));
    }

    private static int part1(HashMap<Point, Integer> energyLevels) {
        int numFlashed = 0;
        for (int step = 0; step < 100; step++) {
            //System.out.println("Step " + (step+1));
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
            for(Point point: points) {
                var adjacentPoints = new HashSet<>(getAdjacentPoints(point));
                flashPoints.addAll(increaseEnergy(adjacentPoints, currentEnergyLevels, hasFlashed));
            }
        }

        // reduce flashed energy to 0
        for(Point point : hasFlashed) {
            currentEnergyLevels.put(point, 0);
        }

        //printEnergyLevels(currentEnergyLevels);
        return hasFlashed.size();
    }

    private static Set<Point> increaseEnergy(Set<Point> points, HashMap<Point, Integer> currentEnergyLevels, Set<Point> hasFlashed) {
        Set<Point> flashPoints = new HashSet<>();

        // increase energy level by 1
        for(Point point : points) {
            currentEnergyLevels.put(point, currentEnergyLevels.get(point) + 1);
            if (currentEnergyLevels.get(point) > 9 && !hasFlashed.contains(point)) {
                flashPoints.add(point);
            }
        }
        return flashPoints;
    }

    private static List<Point> getAdjacentPoints(Point point) {
        if (point.x == 0 && point.y == 0) {
            return List.of(new Point(0, 1), new Point(1, 0), new Point(1, 1));
        } else if (point.x == 0 && point.y == Y_MAX) {
            return List.of(new Point(0, Y_MAX - 1), new Point(1, Y_MAX), new Point(1, Y_MAX - 1));
        } else if (point.x == X_MAX && point.y == 0) {
            return List.of(new Point(X_MAX, 1), new Point(X_MAX - 1, 0), new Point(X_MAX - 1, 1));
        } else if (point.x == X_MAX && point.y == Y_MAX) {
            return List.of(new Point(X_MAX, Y_MAX - 1), new Point(X_MAX - 1, Y_MAX), new Point(X_MAX - 1, Y_MAX - 1));
        } else if (point.x == 0) {
            return List.of(new Point(0, point.y + 1), new Point(1, point.y), new Point(0, point.y - 1), new Point(1, point.y + 1), new Point(1,point.y - 1));
        } else if (point.x == X_MAX) {
            return List.of(new Point(X_MAX, point.y + 1), new Point(X_MAX - 1, point.y), new Point(X_MAX, point.y - 1), new Point(X_MAX -1 , point.y + 1), new Point(X_MAX - 1, point.y - 1));
        } else if (point.y == 0) {
            return List.of(new Point(point.x + 1, 0), new Point(point.x, 1), new Point(point.x - 1, 0), new Point(point.x + 1, 1), new Point(point.x - 1, 1));
        } else if (point.y == Y_MAX) {
            return List.of(new Point(point.x + 1, Y_MAX), new Point(point.x, Y_MAX - 1), new Point(point.x - 1, Y_MAX), new Point(point.x  + 1, Y_MAX-1), new Point(point.x - 1, Y_MAX-1));
        } else {
            return List.of(new Point(point.x, point.y - 1), new Point(point.x, point.y + 1), new Point(point.x - 1, point.y), new Point(point.x + 1, point.y), new Point(point.x + 1, point.y - 1),
                           new Point(point.x + 1, point.y + 1), new Point(point.x - 1, point.y + 1), new Point(point.x - 1, point.y - 1));
        }
    }

    private static void printEnergyLevels(HashMap<Point, Integer> energyLevels) {
        System.out.println("-------------------------");
        for (int y = 0; y <= Y_MAX; y++) {
            for (int x = 0; x <= X_MAX; x++) {
                System.out.print(energyLevels.get(new Point(x, y)));
                System.out.print(", ");
            }
            System.out.print("\n");
        }
        System.out.println("-------------------------");
    }

    private static HashMap<Point, Integer> parseInput() {
        HashMap<Point, Integer> energyLevels = new HashMap<>();
        var in = readInput("day11.txt").collect(Collectors.toList());

        for (int y = 0; y <= Y_MAX; y++) {
            var row = in.get(y).split("");
            for (int x = 0; x <= X_MAX; x++) {
                energyLevels.put(new Point(x, y), Integer.parseInt(row[x]));
            }
        }
        return energyLevels;
    }
}
