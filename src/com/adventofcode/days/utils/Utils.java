package com.adventofcode.days.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    public static void printGrid(HashMap<Point, Integer> gridMap, final int X_MAX, final int Y_MAX) {
        System.out.println("-------------------------");
        for (int y = 0; y <= Y_MAX; y++) {
            for (int x = 0; x <= X_MAX; x++) {
                System.out.print(gridMap.get(new Point(x, y)));
                System.out.print(", ");
            }
            System.out.print("\n");
        }
        System.out.println("-------------------------");
    }

    public static List<Point> getAdjacentPoints(Point point, final int X_MAX, final int Y_MAX, boolean includeDiagonal) {
        List<Point> adjacentPoints;
        List<Point> diagonalPoints = List.of();

        if (point.x == 0 && point.y == 0) {
            adjacentPoints = List.of(new Point(0, 1),
                                     new Point(1, 0),
                                     new Point(1, 1));

        } else if (point.x == 0 && point.y == Y_MAX) {
            adjacentPoints = List.of(new Point(0, Y_MAX - 1),
                                     new Point(1, Y_MAX),
                                     new Point(1, Y_MAX - 1));

        } else if (point.x == X_MAX && point.y == 0) {
            adjacentPoints = List.of(new Point(X_MAX, 1),
                                     new Point(X_MAX - 1, 0),
                                     new Point(X_MAX - 1, 1));

        } else if (point.x == X_MAX && point.y == Y_MAX) {
            adjacentPoints = List.of(new Point(X_MAX, Y_MAX - 1),
                                     new Point(X_MAX - 1, Y_MAX),
                                     new Point(X_MAX - 1, Y_MAX - 1));

        } else if (point.x == 0) {
            diagonalPoints = List.of(new Point(1, point.y + 1),
                                     new Point(1, point.y - 1));
            adjacentPoints = List.of(new Point(0, point.y + 1),
                                     new Point(1, point.y),
                                     new Point(0, point.y - 1));

        } else if (point.x == X_MAX) {
            diagonalPoints = List.of(new Point(X_MAX - 1, point.y + 1),
                                     new Point(X_MAX - 1, point.y - 1));
            adjacentPoints = List.of(new Point(X_MAX, point.y + 1),
                                     new Point(X_MAX - 1, point.y),
                                     new Point(X_MAX, point.y - 1));
        } else if (point.y == 0) {
            diagonalPoints = List.of(new Point(point.x + 1, 1),
                                     new Point(point.x - 1, 1));
            adjacentPoints = List.of(new Point(point.x + 1, 0),
                                     new Point(point.x, 1),
                                     new Point(point.x - 1, 0));
        } else if (point.y == Y_MAX) {
            diagonalPoints = List.of(new Point(point.x + 1, Y_MAX - 1),
                                     new Point(point.x - 1, Y_MAX - 1));
            adjacentPoints = List.of(new Point(point.x + 1, Y_MAX),
                                     new Point(point.x, Y_MAX - 1),
                                     new Point(point.x - 1, Y_MAX));
        } else {
            diagonalPoints = List.of(new Point(point.x + 1, point.y - 1),
                                     new Point(point.x + 1, point.y + 1),
                                     new Point(point.x - 1, point.y + 1),
                                     new Point(point.x - 1, point.y - 1));
            adjacentPoints = List.of(new Point(point.x, point.y - 1),
                                     new Point(point.x, point.y + 1),
                                     new Point(point.x - 1, point.y),
                                     new Point(point.x + 1, point.y));
        }

        List<Point> out = new ArrayList<>(adjacentPoints);
        if (includeDiagonal) {
            out.addAll(diagonalPoints);
        }
        return out;
    }


    // Iterative function to generate all permutations of a string in Java
    // using Collections
    public static List<String> findPermutations(String str) {
        // base case
        if (str == null || str.length() == 0) {
            return null;
        }

        // create an empty ArrayList to store (partial) permutations
        List<String> partial = new ArrayList<>();

        // initialize the list with the first character of the string
        partial.add(String.valueOf(str.charAt(0)));

        // do for every character of the specified string
        for (int i = 1; i < str.length(); i++) {
            // consider previously constructed partial permutation one by one

            // (iterate backward to avoid ConcurrentModificationException)
            for (int j = partial.size() - 1; j >= 0; j--) {
                // remove current partial permutation from the ArrayList
                String s = partial.remove(j);

                // Insert the next character of the specified string at all
                // possible positions of current partial permutation. Then
                // insert each of these newly constructed strings in the list

                for (int k = 0; k <= s.length(); k++) {
                    // Advice: use StringBuilder for concatenation
                    partial.add(s.substring(0, k) + str.charAt(i) + s.substring(k));
                }
            }
        }

        return partial;
    }
}
