package com.adventofcode.days;

import com.adventofcode.days.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


import static com.adventofcode.days.utils.InputReader.readInput;

public class Day8 {
    private static List<List<String>> input;
    private static HashMap<String, Integer> digitMap;

    public static void main(String[] args) {
        parseInput();
        getDigitMap();

        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());;
    }

    private static int part1() {
        int count = 0;
        for (List<String> entry : input) {
            var idx = entry.indexOf("|");
            for (int i = idx + 1; i < entry.size(); i++) {
                count += matchLen2Digit(entry.get(i)) != -1 ? 1 : 0;
            }
        }
        return count;
    }

    private static int part2() {

        var permutations = Utils.findPermutations("abcdefg");
        int sum = 0;
        for (List<String> in : input) {
            sum += calcOutputSum(in, permutations);
        }

        return sum;
    }

    private static int calcOutputSum(List<String> inputLine, List<String> allPermutations) {
        for (String perm : allPermutations) {
            HashMap<String, String> mapping = createMapping(perm);
            boolean match = true;
            List<Integer> fourDigits = new ArrayList<>();
            boolean saveValues = false;
            for (String entry : inputLine) {
                if (entry.equals("|")) {
                    saveValues = true;
                    continue;
                }
                int res = mapToDigit(mapping, entry);

                if (res == -1) {
                    match = false;
                    continue;
                }
                if (saveValues) {
                    fourDigits.add(res);
                }
            }
            if (match) {
                int sum = 0;

                for (int i = 0; i < 4; i++) {
                    sum += fourDigits.get(i) * Math.pow(10, 3-i);
                }
                return sum;
            }
        }
        System.out.println("SOMETHING WENT WRONG");
        return 0;
    }

    private static int mapToDigit(HashMap<String, String> map, String signal) {
        List<String> mappedSignal = new ArrayList<>();
        for (String s : signal.split("")) {
            mappedSignal.add(map.get(s));
        }
        var sig = mappedSignal.stream().sorted().collect(Collectors.joining());
        if (digitMap.containsKey(sig)) {
            return digitMap.get(sig);
        }
        return -1;

    }

    private static HashMap<String, String> createMapping(String inData) {
        HashMap<String, String> map = new HashMap<>();
        List<String> org = List.of("a", "b", "c", "d", "e", "f", "g");
        int idx = 0;
        for (String in : inData.split("")) {
            map.put(in, org.get(idx++));
        }
        return map;
    }


    private static HashMap<String, Integer> getDigitMap() {
        digitMap = new HashMap<>();
        digitMap.put("abcefg", 0);
        digitMap.put("cf", 1);
        digitMap.put("acdeg", 2);
        digitMap.put("acdfg", 3);
        digitMap.put("bcdf", 4);
        digitMap.put("abdfg", 5);
        digitMap.put("abdefg", 6);
        digitMap.put("acf", 7);
        digitMap.put("abcdefg", 8);
        digitMap.put("abcdfg", 9);

        return digitMap;
    }

    private static int matchLen2Digit(String signal) {
        switch (signal.length()) {
            case 2:
                return 1;
            case 4:
                return 4;
            case 3:
                return 7;
            case 7:
                return 8;
            default:
                return -1;

        }
    }

    private static void parseInput() {
        input = new ArrayList<>();
        var in = readInput("day8.txt").collect(Collectors.toList());

        for (String s : in) {
            input.add(Arrays.stream(s.split(" ")).collect(Collectors.toList()));
        }
    }


}
