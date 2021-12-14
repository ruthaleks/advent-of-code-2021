package com.adventofcode.days;

import java.util.HashMap;
import java.util.Objects;

import static com.adventofcode.days.utils.InputParser.parseToList;

public class Day14 {
    private static HashMap<Polymer, String> pairInsertionRules;

    public static void main(String[] args) {
        var polymers = parseInput();

        System.out.println("Part 1 = " + run(10, polymers));
        System.out.println("Part 2 = " + run(40, polymers));
    }

    private static long run(int steps, HashMap<Polymer, Long> initialPolymers) {

        var polymers = initialPolymers;
        for (int step = 0; step < steps; step++) {
            polymers = simulateAStep(polymers);
        }
        var count = countLetters(polymers);

        var mostCommon = count.values().stream().max(Long::compareTo).get();
        var leastCommon = count.values().stream().min(Long::compareTo).get();

        return mostCommon - leastCommon;
    }

    private static HashMap<String, Long> countLetters(HashMap<Polymer, Long> polymers) {
        HashMap<String, Long> out = new HashMap<>();
        for (Polymer polymer : polymers.keySet()) {
            out.put(polymer.second, out.containsKey(polymer.second) ? out.get(polymer.second) + polymers.get(polymer) : polymers.get(polymer));
        }

        var firstLetter = parseToList("day14.txt").get(0).split("")[0];
        out.put(firstLetter, out.get(firstLetter) + 1);

        return out;
    }

    private static HashMap<Polymer, Long> simulateAStep(HashMap<Polymer, Long> polymers) {
        HashMap<Polymer, Long> out = new HashMap<>();
        for (Polymer polymer : polymers.keySet()) {
            String res = pairInsertionRules.get(polymer);
            insert(polymer.first, res, out, polymers.get(polymer));
            insert(res, polymer.second, out, polymers.get(polymer));
        }
        return out;
    }

    private static void insert(String first, String second, HashMap<Polymer, Long> out, long count) {
        var polymer = new Polymer(first, second);
        out.put(polymer, out.containsKey(polymer) ? out.get(polymer) + count : count);
    }


    private static HashMap<Polymer, Long> parseInput() {
        var input = parseToList("day14.txt");

        // polymer template
        HashMap<Polymer, Long> initialPolymerTemplate = new HashMap<>();
        var letters = input.get(0).split("");
        for (int i = 1; i < letters.length; i++) {
            insert(letters[i - 1], letters[i], initialPolymerTemplate, 1);
        }

        // pair insertion rules
        pairInsertionRules = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            var pair = input.get(i).split(" -> ");
            var tmp = pair[0].split("");
            Polymer polymer = new Polymer(tmp[0], tmp[1]);
            pairInsertionRules.put(polymer, pair[1]);
        }
        return initialPolymerTemplate;

    }

    static class Polymer {
        public String first;
        public String second;

        public Polymer(String first, String second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Polymer polymer = (Polymer) o;
            return Objects.equals(first, polymer.first) && Objects.equals(second, polymer.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return "{" + first + "," + second + "}" ;
        }
    }

}
