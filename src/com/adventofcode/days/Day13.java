package com.adventofcode.days;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.adventofcode.days.utils.InputParser.parseToList;

public class Day13 {
    private static List<Map.Entry<String, Integer>> foldingInstructions;

    public static void main(String[] args) {
        var grid = parseInput();
        System.out.println("Part 1 = " + part1(grid));
        part2(grid);
    }

    private static int part1(HashSet<Point> grid) {
        var foldedGrid = fold(foldingInstructions.get(0), grid);
        return foldedGrid.size();
    }

    private static void part2(HashSet<Point> grid) {
        var foldedGrid = grid;
        for (Map.Entry<String, Integer> instruction : foldingInstructions) {
            foldedGrid = fold(instruction, foldedGrid);
        }
        printMessage(foldedGrid);
    }

    private static void printMessage(HashSet<Point> grid) {
        Point maxCoordinates = findMax(grid);
        final int X_MAX = maxCoordinates.x;
        final int Y_MAX = maxCoordinates.y;
        for (int y = 0; y <= Y_MAX; y++) {
            for (int x = 0; x <= X_MAX; x++) {
                if (grid.contains(new Point(x, y))) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    private static Point findMax(HashSet<Point> grid) {
        Point maxPoint = new Point(0, 0);
        for (Point point : grid) {
            maxPoint.x = Math.max(maxPoint.x, point.x);
            maxPoint.y = Math.max(maxPoint.y, point.y);
        }
        return maxPoint;
    }

    private static HashSet<Point> fold(Map.Entry<String, Integer> instruction, HashSet<Point> grid) {

        return Objects.equals(instruction.getKey(), "y") ? foldUp(instruction.getValue(), grid) : foldLeft(instruction.getValue(), grid);
    }

    private static HashSet<Point> foldUp(final int POS, HashSet<Point> grid) {
        HashSet<Point> reducedGrid = new HashSet<>();
        for (Point point : grid) {
            if (point.y < POS) {
                reducedGrid.add(point);
            } else if (point.y > POS) {
                reducedGrid.add(new Point(point.x, POS * 2 - point.y));
            }
        }
        return reducedGrid;
    }

    private static HashSet<Point> foldLeft(final int POS, HashSet<Point> grid) {
        HashSet<Point> reducedGrid = new HashSet<>();
        for (Point point : grid) {
            if (point.x < POS) {
                reducedGrid.add(point);
            } else if (point.x > POS) {
                reducedGrid.add(new Point(POS * 2 - point.x, point.y));
            }
        }
        return reducedGrid;
    }


    private static HashSet<Point> parseInput() {
        var input = parseToList("day13.txt");

        HashSet<Point> grid = new HashSet<>();
        foldingInstructions = new ArrayList<>();
        for (String line : input) {
            if (line.equals("")) {
                break;
            }
            var tmp = line.split(",");
            int x = Integer.parseInt(tmp[0]);
            int y = Integer.parseInt(tmp[1]);

            grid.add(new Point(x, y));
        }

        for (int i = input.indexOf("") + 1; i < input.size(); i++) {
            var tmp = input.get(i).split(" ")[2].split("=");
            var key = tmp[0];
            var value = Integer.parseInt(tmp[1]);
            foldingInstructions.add(Map.entry(key, value));
        }
        return grid;

    }

}
