package com.adventofcode.days;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day17 {
    // target area: x=282..314, y=-80..-45
    private static final Map.Entry<Integer, Integer> X_LIMIT = Map.entry(282, 314);
    private static final Map.Entry<Integer, Integer> Y_LIMIT = Map.entry(-80, -45);

    public static void main(String[] args) {
        Set<Point> targetArea = getTargetArea();
        System.out.println("Part 1 = " + part1(targetArea));
        System.out.println("Part 2 = " + part2(targetArea));
    }

    private static int part1(Set<Point> targetArea) {
        var velocities = findValidVelocities(targetArea);
        int out = 0;

        for (Point vel : velocities) {
            var trajectory = simulateTrajectory(vel, targetArea);
            int topHeight = getMaxHeight(trajectory);
            out = Math.max(topHeight, out);
        }
        return out;
    }

    private static int part2(Set<Point> targetArea) {
        return findValidVelocities(targetArea).size();
    }

    private static Set<Point> findValidVelocities(Set<Point> targetArea) {
        Set<Point> validVelocities = new HashSet<>();
        for (int x = 1; x < 1000; x++) {
            for (int y = -100; y < 100; y++) {
                List<Point> trajectory = simulateTrajectory(new Point(x, y), targetArea);
                if (targetArea.contains(trajectory.get(trajectory.size() - 1))) {
                    validVelocities.add(new Point(x, y));
                }
            }
        }
        return validVelocities;
    }

    private static int getMaxHeight(List<Point> trajectory) {
        int max = 0;
        for (Point step : trajectory) {
            if (step.y >= max) {
                max = step.y;
            } else {
                break;
            }
        }
        return max;
    }

    private static List<Point> simulateTrajectory(Point velocity, Set<Point> targetArea) {
        List<Point> trajectory = new ArrayList<>();
        Point currentPos = new Point(0, 0);
        Point currentVel = velocity;

        while (!targetArea.contains(currentPos) && !missedTarget(currentPos, currentVel)) {
            trajectory.add(currentPos);
            var next = simulateAStep(currentPos, currentVel);
            currentPos = next.getKey();
            currentVel = next.getValue();
        }
        trajectory.add(currentPos);
        return trajectory;
    }

    private static boolean missedTarget(Point pos, Point vel) {
        if (pos.x < X_LIMIT.getKey() && vel.x == 0) {
            return true;
        }
        if (pos.x > X_LIMIT.getValue()) {
            return true;
        }
        return pos.y < Y_LIMIT.getKey();
    }

    private static Map.Entry<Point, Point> simulateAStep(Point currentPos, Point velocity) {
        int x = currentPos.x + velocity.x;
        int y = currentPos.y + velocity.y;

        int xVel = 0;
        if (velocity.x != 0) {
            xVel = velocity.x > 0 ? velocity.x - 1 : velocity.x + 1;
        }
        int yVel = velocity.y - 1;

        return Map.entry(new Point(x, y), new Point(xVel, yVel));
    }

    private static Set<Point> getTargetArea() {
        Set<Point> out = new HashSet<>();
        for (int x = X_LIMIT.getKey(); x <= X_LIMIT.getValue(); x++) {
            for (int y = Y_LIMIT.getKey(); y <= Y_LIMIT.getValue(); y++) {
                out.add(new Point(x, y));
            }
        }
        return out;
    }
}
