package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputParser.readInput;

public class Day4 {
    private static List<Integer> drawnNumbers;
    private static List<int[][]> board;
    private static List<int[][]> match;
    private static Integer NUMBER_OF_BOARDS;
    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        parseInput();
        initMatchBord();
        if (DEBUG) {
            System.out.println(NUMBER_OF_BOARDS);
        }

        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());

    }

    private static int part1() {

        for (Integer number : drawnNumbers) {
            for (int i = 0; i < NUMBER_OF_BOARDS; i++) {
                findMatch(i, number);
                if (isBingo(i)) {
                    if (DEBUG) {
                        System.out.println("BINGO!");
                        System.out.println("Board number = " + (i + 1));
                        System.out.println(calcUnchecked(i));
                        System.out.println(number);
                    }

                    return calcUnchecked(i) * number;
                }
            }
        }
        System.out.println("NO BINGO!");
        return 0;
    }

    private static int part2() {
        List<Integer> wonBingBoards = new ArrayList<>(Collections.nCopies(NUMBER_OF_BOARDS, 0));
        for (Integer number : drawnNumbers) {
            for (int i = 0; i < NUMBER_OF_BOARDS; i++) {
                findMatch(i, number);
                if (isBingo(i)) {
                    wonBingBoards.set(i, 1);
                    if (wonBingBoards.stream().mapToInt(Integer::intValue).sum() == NUMBER_OF_BOARDS) {
                        if (DEBUG) {
                            System.out.println("Last to win!");
                            System.out.println("Board number = " + (i + 1));
                            System.out.println(calcUnchecked(i));
                            System.out.println(number);
                        }
                        return calcUnchecked(i) * number;
                    }
                }
            }
        }
        System.out.println("NO BINGO!");
        return 0;
    }

    private static int calcUnchecked(int boardNumber) {
        int sum = 0;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                if (match.get(boardNumber)[row][col] == 0) {
                    sum += board.get(boardNumber)[row][col];
                }
            }
        }
        return sum;
    }

    private static boolean isBingo(int boardNumber) {
        for (int[] rows : match.get(boardNumber)) {
            if (Arrays.stream(rows).sum() == 5) {
                return true;
            }
        }
        for (int col = 0; col < 5; col++) {
            int sum = 0;
            for (int row = 0; row < 5; row++) {
                sum += match.get(boardNumber)[row][col];
            }
            if (sum == 5) {
                return true;
            }
        }
        return false;
    }

    private static void findMatch(int boardNumber, int number) {
        var subBoard = board.get(boardNumber);
        for (int i = 0; i < 5; i++) {
            var idx = Arrays.stream(subBoard[i]).boxed().collect(Collectors.toList()).indexOf(number);
            if (idx >= 0) {
                match.get(boardNumber)[i][idx] = 1;
                return;
            }
        }
    }

    private static void initMatchBord() {
        match = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_BOARDS; i++) {
            int[][] subBoard = new int[5][5];
            match.add(subBoard);
        }
    }

    private static void parseInput() {
        var in = readInput("day4.txt").collect(Collectors.toList());
        drawnNumbers = Arrays.stream(in.get(0).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        board = new ArrayList<>();
        NUMBER_OF_BOARDS = (in.size() - 1) / 6;
        for (int b = 0; b < NUMBER_OF_BOARDS; b++) {
            int[][] subBoard = new int[5][5];
            for (int i = b * 6 + 2; i < (b + 1) * 6 + 1; i++) {
                var line = Arrays.stream(in.get(i).strip().split("\\s+")).map(String::strip).mapToInt(Integer::parseInt).toArray();
                subBoard[i - 2 - b * 6] = line;
            }
            board.add(subBoard);
        }
    }
}
