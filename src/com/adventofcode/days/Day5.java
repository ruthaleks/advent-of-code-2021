package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputReader.readInput;

public class Day5 {
    private static List<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> input;

    public static void main(String[] args) {
        var map = parseInputMap();
        //System.out.println(input);
        System.out.println("Part 1 = " + part1(map));

        var map2 = parseInputMap();
        System.out.println("Part 2 = " + part2(map2));
        //System.out.println(map);
    }

    private static int part1(HashMap<Map.Entry<Integer, Integer>, Integer> map) {
        for (Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> line : input) {
            if (isStraight(line)) {
                var coordinates = getCoordinates(line);
                for (Map.Entry<Integer, Integer> entry : coordinates) {
                    map.put(entry, map.containsKey(entry) ? 1 : 0);
                }
            }
        }
        return map.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static int part2(HashMap<Map.Entry<Integer, Integer>, Integer> map) {
        for (Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> line : input) {
            var coordinates = isStraight(line) ? getCoordinates(line) : getCoordinatesDiag(line);
            for (Map.Entry<Integer, Integer> entry : coordinates) {
                map.put(entry, map.containsKey(entry) ? 1 : 0);
            }
        }
        return map.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static boolean isStraight(Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> in) {
        int x1 = in.getKey().getKey();
        int x2 = in.getValue().getKey();
        int y1 = in.getKey().getValue();
        int y2 = in.getValue().getValue();

        return x1 == x2 || y1 == y2;
    }

    private static List<Map.Entry<Integer, Integer>> getCoordinatesDiag(Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> line) {
        var xy1 = line.getKey();
        var xy2 = line.getValue();

        int xDiff = xy2.getKey() - xy1.getKey();
        int yDiff = xy2.getValue() - xy1.getValue();

        int xCurrent = xy1.getKey();
        int yCurrent = xy1.getValue();
        List<Map.Entry<Integer, Integer>> coordinates = new ArrayList<>();
        while (xCurrent != xy2.getKey()) {
            coordinates.add(Map.entry(xCurrent, yCurrent));
            xCurrent = xDiff > 0 ? xCurrent + 1 : xCurrent - 1;
            yCurrent = yDiff > 0 ? yCurrent + 1 : yCurrent - 1;
        }
        coordinates.add(xy2);

        return coordinates;
    }

    private static List<Map.Entry<Integer, Integer>> getCoordinates(Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> line) {
        var xy1 = line.getKey();
        var xy2 = line.getValue();

        List<Map.Entry<Integer, Integer>> coordinates = new ArrayList<>();
        var xLimit = getLimits(xy1.getKey(), xy2.getKey());
        for (int x = xLimit.get(0); x < xLimit.get(1); x++) {
            coordinates.add(Map.entry(x, xy1.getValue()));
        }

        var yLimit = getLimits(xy1.getValue(), xy2.getValue());
        for (int y = yLimit.get(0); y < yLimit.get(1); y++) {
            coordinates.add(Map.entry(xy1.getKey(), y));
        }

        return coordinates;
    }

    private static List<Integer> getLimits(int c1, int c2) {
        int diff = c2 - c1;
        if (diff == 0)
            return List.of(0, 0);
        int init = Math.min(c1, c1 + diff);
        int max = init + Math.abs(diff) + 1;
        return List.of(init, max);
    }


    private static HashMap<Map.Entry<Integer, Integer>, Integer> parseInputMap() {
        var in = readInput("day5.txt").collect(Collectors.toList());
        HashMap<Map.Entry<Integer, Integer>, Integer> map = new HashMap<>();
        input = new ArrayList<>();

        for (String s : in) {
            var line = Arrays.stream(s.split("->")).map(String::strip).collect(Collectors.toList());
            var xy1 = getCoordinate(line.get(0));
            var xy2 = getCoordinate(line.get(1));
            input.add(Map.entry(xy1, xy2));
        }
        return map;
    }

    private static Map.Entry<Integer, Integer> getCoordinate(String input) {
        var xy = input.split(",");
        Integer x = Integer.parseInt(xy[0]);
        Integer y = Integer.parseInt(xy[1]);
        return Map.entry(x, y);


    }
}
