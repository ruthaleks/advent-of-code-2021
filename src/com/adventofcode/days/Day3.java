package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputParser.readInput;

public class Day3 {

    private final static int INPUT_LENGTH = 12;

    public static void main(String[] args) {
        int[][] input = parseInput();
        System.out.println("Part 1 = " + part1(input));
        System.out.println("Part 2 = " + part2(input));
    }

    private static int part1(int[][] input) {
        List<Integer> gammaRate = calcGammaRate(input);
        List<Integer> epsilonRate = calcEpsilonRate(gammaRate);
        return binToDec(gammaRate) * binToDec(epsilonRate);
    }

    private static int part2(int[][] input) {
        int o = calcOxygenRate(input);
        int c = calcC02Scrub(input);

        return o * c;
    }

    private static int calcC02Scrub(int[][] input) {
        List<Integer> activeRows = new ArrayList<>(Collections.nCopies(input.length, 1));
        List<Integer> co2Scrub = new ArrayList<>();
        for (int col = 0; col < input[0].length; col++) {
            int numActiveRows = activeRows.stream().mapToInt(Integer::intValue).sum();
            if (numActiveRows == 1) {
                co2Scrub.add(input[activeRows.indexOf(1)][col]);
            } else {
                int leastCommon = calMostCommon(input, col, activeRows) == 1 ? 0 : 1;
                co2Scrub.add(leastCommon);
                for (int i = 0; i < input.length; i++) {
                    if (input[i][col] != leastCommon) {
                        activeRows.set(i, 0);
                    }
                }
            }

        }
        return binToDec(co2Scrub);
    }

    private static int calcOxygenRate(int[][] input) {
        List<Integer> activeRows = new ArrayList<>(Collections.nCopies(input.length, 1));
        List<Integer> oxygenGenRate = new ArrayList<>();
        for (int col = 0; col < input[0].length; col++) {
            int numActiveRows = activeRows.stream().mapToInt(Integer::intValue).sum();
            if (numActiveRows == 1) {
                oxygenGenRate.add(input[activeRows.indexOf(1)][col]);
            } else {
                int mostCommon = calMostCommon(input, col, activeRows);
                oxygenGenRate.add(mostCommon);
                for (int i = 0; i < input.length; i++) {
                    if (input[i][col] != mostCommon) {
                        activeRows.set(i, 0);
                    }
                }
            }

        }
        return binToDec(oxygenGenRate);
    }

    private static int calMostCommon(int[][] input, int col, List<Integer> active) {
        int sum = 0;
        int numRows = input.length;
        int numActiveRows = active.stream().mapToInt(Integer::intValue).sum();

        for (int i = 0; i < numRows; i++) {
            if (active.get(i) == 1) {
                sum += input[i][col];
            }
            if (sum > numActiveRows / 2) {
                return 1;
            }
        }
        return sum >= numActiveRows - sum ? 1 : 0;
    }

    private static List<Integer> calcEpsilonRate(List<Integer> gammaRate) {
        // inverse of gamma rate
        List<Integer> epsilonRateBin = new ArrayList<>();

        for (Integer i : gammaRate) {
            epsilonRateBin.add(i == 0 ? 1 : 0);
        }
        return epsilonRateBin;
    }

    private static List<Integer> calcGammaRate(int[][] input) {
        int numRows = input.length;
        int numCols = input[0].length;

        List<Integer> gammaRateBin = new ArrayList<>(Collections.nCopies(INPUT_LENGTH, 0));

        for (int c = 0; c < numCols; c++) {
            int sum = 0;
            for (int[] ints : input) {
                sum += ints[c];
                if (sum > numRows / 2) {
                    gammaRateBin.set(c, 1);
                    break;
                }
            }
        }
        return gammaRateBin;
    }

    private static int binToDec(List<Integer> bin) {
        int dec = 0;
        for (int i = 0; i < bin.size(); i++) {
            dec += bin.get(i) * Math.pow(2, bin.size() - i - 1);
        }
        return dec;
    }

    private static int[][] parseInput() {
        var in = readInput("day3.txt").collect(Collectors.toList());
        int rows = in.size();
        int[][] matrix = new int[rows][INPUT_LENGTH];
        for (int i = 0; i < rows; i++) {
            String[] row = in.get(i).split("");
            for (int j = 0; j < INPUT_LENGTH; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }
        return matrix;
    }
}
