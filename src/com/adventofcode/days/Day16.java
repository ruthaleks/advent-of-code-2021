package com.adventofcode.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.adventofcode.days.utils.InputParser.parseToList;

public class Day16 {
    private static final Map<String, String> hexToBin = Map.ofEntries(Map.entry("0", "0000"), Map.entry("1", "0001"),
                                                                      Map.entry("2", "0010"), Map.entry("3", "0011"),
                                                                      Map.entry("4", "0100"), Map.entry("5", "0101"),
                                                                      Map.entry("6", "0110"), Map.entry("7", "0111"),
                                                                      Map.entry("8", "1000"), Map.entry("9", "1001"),
                                                                      Map.entry("A", "1010"), Map.entry("B", "1011"),
                                                                      Map.entry("C", "1100"), Map.entry("D", "1101"),
                                                                      Map.entry("E", "1110"), Map.entry("F", "1111"));
    private static final Map<Integer, Integer> lengthTypeIdToLengthInBits = Map.of(0, 15, 1, 11);

    private static int currentIdx = 0;

    private static int versionSum = 0;

    private static final int SUM = 0;
    private static final int PRODUCT = 1;
    private static final int MINIMUM = 2;
    private static final int MAXIMUM = 3;
    private static final int GREATER_THAN = 5;
    private static final int LESS_THAN = 6;
    private static final int EQUAL = 7;


    public static void main(String[] args) {
        var hex = parseInput();
        System.out.println(hex);
        var bin = convertToBinStream(hex);
        long res = parsePackage(bin);

        System.out.println("Part 1 = " + versionSum);
        System.out.println("Part 2 = " + res);
        //Part 1 = 927
    }

    private static long parsePackage(List<String> input) {
        Header header = parseHeader(input);
        long out = -1;
        if(header.id == 4) {
            out = literal(input);
        } else {
            // operator
            List<Long> res = parse(header, input);
            res = res.stream().filter(integer -> integer > -1).collect(Collectors.toList());
            System.out.println(res);

            if (header.id == SUM) {
                out = res.stream().mapToLong(Long::longValue).sum();
            } else if (header.id == PRODUCT) {
                long product = 1;
                for(long i : res) {
                    product *= i;
                }
                out = product;
            } else if (header.id == MINIMUM) {
                out = res.stream().mapToLong(Long::longValue).min().getAsLong();
            } else if (header.id == MAXIMUM) {
                out = res.stream().mapToLong(Long::longValue).max().getAsLong();
            } else if (header.id == GREATER_THAN) {
                out = res.get(0) > res.get(1) ? 1 : 0;
            } else if (header.id == LESS_THAN) {
                out = res.get(0) < res.get(1) ? 1 : 0;
            } else {
                out = Objects.equals(res.get(0), res.get(1)) ? 1 : 0;
            }
        }
        return out;
    }

    private static List<Long> parse(Header header, List<String> input) {
        List<Long> out = new ArrayList<>();
        if (header.lengthId == 0) {
            int max = currentIdx + header.length;
            while (currentIdx < max) {
                out.add(parsePackage(input));
            }
        } else {
            int max = currentIdx + header.length;
            for (int i = 0; i < header.length ; i++){
                out.add(parsePackage(input));
            }
        }

        return out;
    }

    private static Header parseHeader(List<String> input) {
        int version = version(input);
        versionSum += version;
        int id = id(input);

        Header header = new Header(version, id);
        if (id != 4) {
            header.lengthId = lengthTypeId(input);
            header.length = length(input, header.lengthId);
        }

        return header;
    }

    private static int lengthTypeId(List<String> input) {
        return (int) binToDec(readBits(input, 6, 1));
    }

    private static int lengthInBits(List<String> input, int id) {
        return lengthTypeIdToLengthInBits.get(id);
    }

    private static int length(List<String> input, int id) {
        return (int) binToDec(readBits(input, 7, lengthInBits(input, id)));
    }

    private static List<Long> readBits(List<String> input, int start, int len) {
        List<Long> bin = new ArrayList<>();
        for (int i = currentIdx; i < currentIdx + len; i++) {
            bin.add(Long.parseLong(input.get(i)));
        }
        currentIdx += len;
        return bin;
    }

    private static int version(List<String> input) {
        return (int) binToDec(readBits(input, 0, 3));
    }

    private static int id(List<String> input) {
        return (int) binToDec(readBits(input, 3, 3));
    }

    private static long literal(List<String> input) {
        int idx = currentIdx;
        boolean cont = readBits(input, idx, 1).get(0) == 1;
        List<Long> total = new ArrayList<>(readBits(input, idx, 4));

        while (cont) {
            cont = readBits(input, idx, 1).get(0) == 1;
            total.addAll(readBits(input, idx, 4));
        }
        return binToDec(total);
    }

    private static long binToDec(List<Long> bin) {
        long dec = 0;
        for (int i = 0; i < bin.size(); i++) {
            dec += bin.get(i) * Math.pow(2, bin.size() - i - 1);
        }
        return dec;
    }

    private static List<String> convertToBinStream(List<String> hexStream) {
        List<String> out = new ArrayList<>();
        for (String hex : hexStream) {
            out.addAll(Arrays.stream(hexToBin.get(hex).split("")).collect(Collectors.toList()));
        }
        return out;
    }


    private static List<String> parseInput() {
        List<String> input = parseToList("day16.txt");

        if (input.size() == 1) {
            return Arrays.stream(input.get(0).split("")).collect(Collectors.toList());
        }

        return null;
    }

    static class Header {
        public int version;
        public int id;
        public int lengthId;
        public int length;

        public Header(int version, int id) {
            this.version = version;
            this.id = id;
        }

        @Override
        public String toString() {
            return "Header{" +
                   "version=" + version +
                   ", id=" + id +
                   ", lengthId=" + lengthId +
                   ", length=" + length +
                   '}';
        }
    }

}
