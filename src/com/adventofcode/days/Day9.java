package com.adventofcode.days;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputParser.parseFinalGridListToMap;
import static com.adventofcode.days.utils.InputParser.parseToList;
import static com.adventofcode.days.utils.Utils.getAdjacentPoints;

public class Day9 {
    private static HashMap<Point, Integer> inputMap;
    private static int X_MAX;
    private static int Y_MAX;

    public static void main(String[] args) {
        parseInput();
        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());
    }

    private static int part1() {
        List<Point> lowPoints = findLowPoints();

        int res = 0;
        for (Point p : lowPoints) {
            res += inputMap.get(p) + 1;
        }

        return res;
    }

    private static int part2() {
        List<Point> lowPoints = findLowPoints();
        List<Integer> basinSizes = new ArrayList<>();
        for (Point p : lowPoints) {
            HashSet<Point> basin = new HashSet<>();
            basin = (HashSet<Point>) getBasin(basin, List.of(p));
            int basinSize = basin.size();
            basinSizes.add(basinSize);
        }

        // find the three largest
        basinSizes = basinSizes.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        return basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
    }

    private static Set<Point> getBasin(HashSet<Point> basin, List<Point> points) {
        List<Point> newPoints = new ArrayList<>();
        for (Point point : points) {
            basin.add(point);
            var tmpBasin = new HashSet<>(basin);
            List<Point> adjacentPoints = getAdjacentPoints(point, X_MAX, Y_MAX, false);
            adjacentPoints = adjacentPoints.stream().filter(p -> inputMap.get(p) < 9).collect(Collectors.toList());
            adjacentPoints = adjacentPoints.stream().filter(p -> !tmpBasin.contains(p)).collect(Collectors.toList());
            newPoints.addAll(adjacentPoints);
        }

        if (newPoints.isEmpty()) {
            return basin;
        }
        return getBasin(basin, newPoints);
    }

    private static List<Point> findLowPoints() {
        List<Point> lowPoints = new ArrayList<>();
        for (int y = 0; y <= Y_MAX; y++) {
            for (int x = 0; x <= X_MAX; x++) {
                Point point = new Point(x, y);
                List<Point> adjacentPoints = getAdjacentPoints(point, X_MAX, Y_MAX, false);
                int pointValue = inputMap.get(point);
                boolean isLowPoint = true;
                for (Point p : adjacentPoints) {
                    if (inputMap.get(p) <= pointValue) {
                        isLowPoint = false;
                        break;
                    }
                }
                if (isLowPoint) {
                    lowPoints.add(point);
                }
            }
        }
        return lowPoints;
    }

    private static void parseInput() {
        var input = parseToList("day9.txt");
        X_MAX = input.get(0).length() - 1;
        Y_MAX = input.size() - 1;

        inputMap = parseFinalGridListToMap(input, "");
    }
}

