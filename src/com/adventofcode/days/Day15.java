package com.adventofcode.days;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.adventofcode.days.utils.InputParser.parseFinalGridListToMap;
import static com.adventofcode.days.utils.InputParser.parseToList;
import static com.adventofcode.days.utils.Utils.getAdjacentPoints;

public class Day15 {

    private static int X_MAX;
    private static int Y_MAX;

    public static void main(String[] args) {
        HashMap<Point, Integer> costMap = parseInput();

        System.out.println("Part 1 = " + part1(costMap));
        System.out.println("Part 2 = " + part2(costMap));

    }

    private static int part1(HashMap<Point, Integer> costMap) {
        return dijkstra(costMap, X_MAX, Y_MAX);
    }

    private static int part2(HashMap<Point, Integer> initialCostMap) {
        int x_max = (X_MAX + 1) * 5 - 1;
        int y_max = (Y_MAX + 1) * 5 - 1;
        var costMap = enlarge(x_max, y_max, initialCostMap);
        //printGrid(costMap, x_max, y_max);

        return dijkstra(costMap, x_max, y_max);
    }

    private static HashMap<Point, Integer> enlarge(final int x_max, final int y_max, HashMap<Point, Integer> initialCostMap) {
        HashMap<Point, Integer> out = new HashMap<>();
        for (int y = 0; y <= y_max; y++) {
            for (int x = 0; x <= x_max; x++) {
                int cost = initialCostMap.get(new Point(x % (X_MAX + 1), y % (Y_MAX + 1))) + x / (X_MAX + 1) + y / (Y_MAX + 1);
                //System.out.println(9 / X_MAX);
                if (cost > 9) {
                    cost = cost % 9;
                }
                out.put(new Point(x, y), cost);
            }
        }
        return out;
    }

    private static int dijkstra(HashMap<Point, Integer> costMap, final int x_max, final int y_max) {
        Set<Point> unVisited = new HashSet<>();
        Set<Point> visited = new HashSet<>();

        var costs = initCosts(x_max, y_max);

        // init
        Point startPoint = new Point(0, 0);
        Point endPoint = new Point(x_max, y_max);
        costs.put(startPoint, 0);
        unVisited.add(startPoint);

        // run
        while (!unVisited.isEmpty()) {
            Point currentPoint = getNextPoint(unVisited, costs);
            List<Point> adjPoints = getAdjacentPoints(currentPoint, x_max, y_max, false);
            for (Point adjPoint : adjPoints) {
                if (!visited.contains(adjPoint)) {
                    if ((costs.get(currentPoint) + costMap.get(adjPoint)) < costs.get(adjPoint)) {
                        costs.put(adjPoint, costs.get(currentPoint) + costMap.get(adjPoint));
                    }
                    unVisited.add(adjPoint);
                }
            }
            visited.add(currentPoint);
            if (visited.contains(endPoint)) {
                return costs.get(endPoint);
            }
        }
        return 0;
    }


    private static Point getNextPoint(Set<Point> points, HashMap<Point, Integer> costs) {
        Point out = null;
        for (Point point : points) {
            if (out == null) {
                out = point;
                continue;
            }
            if (costs.get(point) < costs.get(out)) {
                out = point;
            }
        }
        points.remove(out);
        return out;
    }

    private static HashMap<Point, Integer> initCosts(int x_max, int y_max) {
        HashMap<Point, Integer> out = new HashMap<>();
        for (int y = 0; y <= y_max; y++) {
            for (int x = 0; x <= x_max; x++) {
                out.put(new Point(x, y), Integer.MAX_VALUE);
            }
        }
        return out;
    }


    private static HashMap<Point, Integer> parseInput() {
        List<String> input = parseToList("day15.txt");

        X_MAX = input.get(0).length() - 1;
        Y_MAX = input.size() - 1;

        return parseFinalGridListToMap(input, "");
    }

   
}
