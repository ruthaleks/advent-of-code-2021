package com.adventofcode.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.adventofcode.days.utils.InputParser.parseToList;

public class Day12 {
    private static HashMap<String, Set<String>> map;

    public static void main(String[] args) {
        parseInput();
        System.out.println("Part 1 = " + part1());
        System.out.println("Part 2 = " + part2());

    }

    private static int part1() {
        Link root = new Link("start");
        var tree = new Tree(root);
        var paths = genTreeMap(root, tree, false);

        int count = 0;
        for (Link path : paths) {
            if (path.value.equals("end")){
                count++;
            }
        }
        return count;
    }

    private static int part2() {
        Link root = new Link("start");
        var tree = new Tree(root);
        var paths = genTreeMap(root, tree, true);

        int count = 0;
        for (Link path : paths) {
            if (path.value.equals("end")){
                count++;
            }
        }
        return count;
    }

    private static List<Link> genTreeMap(Link root, Tree tree, boolean isPart2) {
        var childLinks = insertInTree(root, tree, isPart2);
        List<Link> pathsLinks = new ArrayList<>();

        while (!childLinks.isEmpty()) {
            var parentLinks = new ArrayList<>(childLinks);
            childLinks = new ArrayList<>();
            for (Link parent : parentLinks) {
                var c = insertInTree(parent, tree, isPart2);
                if (c.isEmpty()) {
                    pathsLinks.add(parent);
                }
                childLinks.addAll(c);
            }
        }
        return pathsLinks;
    }

    private static List<Link> insertInTree(Link parent, Tree tree, boolean part2) {
        if (Objects.equals(parent.value, "end")) {
            return List.of();
        }
        List<Link> out = new ArrayList<>();
        for (String value : map.get(parent.value)) {
            Link link = new Link(value, parent, parent.visitedOneSmallCanTwice);
            if (part2) {
                if(tree.smallCaveVisited(link, parent) && !parent.visitedOneSmallCanTwice) {
                    link.visitedOneSmallCanTwice = true;
                    out.add(link);
                }

            }
            if (!tree.smallCaveVisited(link, parent)) {
                out.add(link);
            }

        }
        return out;
    }


    private static void parseInput() {
        var input = parseToList("day12.txt");

        // key = in, value = possible outs
        map = new HashMap<>();
        for (String entry : input) {
            var tmp = entry.split("-");
            String node1 = tmp[0];
            String node2 = tmp[1];
            if (!node2.equals("start")) {
                if (map.containsKey(node1)) {
                    map.get(node1).add(node2);
                } else {
                    map.put(node1, Stream.of(node2).collect(Collectors.toCollection(HashSet::new)));
                }
            }
            if (!node2.equals("end") && !node1.equals("start")) {
                if (map.containsKey(node2)) {
                    map.get(node2).add(node1);
                } else {
                    map.put(node2, Stream.of(node1).collect(Collectors.toCollection(HashSet::new)));
                }
            }

        }
    }

    static class Tree {
        public Link root;

        public Tree(Link root) {
            this.root = root;
        }

        public void printFromLink(Link endLink) {
            while (endLink != null) {
                System.out.print(endLink + " -> ");
                endLink = endLink.previous;
            }
            System.out.print("\n");
        }

        public boolean smallCaveVisited(Link link, Link endLink) {
            String value = link.value;
            if (value.equals(value.toLowerCase())) {
                while (endLink != null) {
                    if (endLink.value.equals(value)) {
                        return true;
                    }
                    endLink = endLink.previous;
                }
            }
            return false;
        }
    }

    static class Link {
        public String value;
        public Link previous;
        public boolean visitedOneSmallCanTwice = false;

        public Link(String value) {
            this.value = value;
            this.previous = null;
        }

        public Link(String value, Link previous) {
            this(value);
            this.previous = previous;
        }

        public Link(String value, Link previous, boolean visitedOneSmallCanTwice) {
            this(value, previous);
            this.visitedOneSmallCanTwice = visitedOneSmallCanTwice;
        }

        @Override
        public String toString() {
            return "Link{" +
                   "value='" + value + '\'' +
                   '}';
        }
    }

}
