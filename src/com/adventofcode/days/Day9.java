package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputReader.readInput;

public class Day9 {
    private static HashMap<Point, Integer> inputMap;
    private static int X_MAX;
    private static int Y_MAX;

    public static void main(String[] args) {
        parseInput();
        //System.out.println(inputMap);
        //System.out.println(inputMap.get(new Point(9, 4)));
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
            //System.out.println(basin);
            assert basin != null;
            int basinSize = basin.size();
           // System.out.println("LowPoint = " + lowPoints.get(1) + " size = " + basinSize);
           // System.out.println(basin);

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
            List<Point> adjacentPoints = getAdjacentPoints(point);
            adjacentPoints = adjacentPoints.stream().filter(p -> inputMap.get(p) < 9).collect(Collectors.toList());
            adjacentPoints = adjacentPoints.stream().filter(p -> !tmpBasin.contains(p)).collect(Collectors.toList());
            newPoints.addAll(adjacentPoints);
        }

        if (newPoints.isEmpty()) {
          //  System.out.println("final basin  = " + basin );
            return basin;
        }
        return getBasin(basin, newPoints);
    }

    private static List<Point> findLowPoints() {
        List<Point> lowPoints = new ArrayList<>();
        for (int y = 0; y <= Y_MAX; y++) {
            for (int x = 0; x <= X_MAX; x++) {
                Point point = new Point(x, y);
                List<Point> adjacentPoints = getAdjacentPoints(point);
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
            return List.of(new Point(0, point.y + 1), new Point(1, point.y), new Point(0, point.y - 1));
        } else if (point.x == X_MAX) {
            return List.of(new Point(X_MAX, point.y + 1), new Point(X_MAX - 1, point.y), new Point(X_MAX, point.y - 1));
        } else if (point.y == 0) {
            return List.of(new Point(point.x + 1, 0), new Point(point.x, 1), new Point(point.x - 1, 0));
        } else if (point.y == Y_MAX) {
            return List.of(new Point(point.x + 1, Y_MAX), new Point(point.x, Y_MAX - 1), new Point(point.x - 1, Y_MAX));
        } else {
            return List.of(new Point(point.x, point.y - 1), new Point(point.x, point.y + 1), new Point(point.x - 1, point.y), new Point(point.x + 1, point.y));
        }
    }

    private static void parseInput() {
        inputMap = new HashMap<>();
        var in = readInput("day9.txt").collect(Collectors.toList());
        in.forEach(System.out::println);
        X_MAX = in.get(0).length() - 1;
        Y_MAX = in.size() - 1;
        for (int y = 0; y <= Y_MAX; y++) {
            var row = in.get(y).split("");
            for (int x = 0; x <= X_MAX; x++) {
                inputMap.put(new Point(x, y), Integer.parseInt(row[x]));
            }
        }
    }

    static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                   "x=" + x +
                   ", y=" + y +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}

