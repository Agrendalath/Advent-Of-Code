package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Day13 {
    private final static int MAP_SIZE = 1000;
    private final static int NOT_CALCULATED = -1;

    private static boolean checkPoint(int x, int y, int[][] map) {
        return x >= 0 && y >= 0 && map[x][y] == NOT_CALCULATED;
    }

    private static boolean move(Point previousPoint, Point to, final int FAVORITE_NUMBER, int[][] map) {
        int x = to.x;
        int y = to.y;
        if (checkPoint(x, y, map) &&
                Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + FAVORITE_NUMBER) % 2 == 0) {
            map[x][y] = map[previousPoint.x][previousPoint.y] + 1;
            return true;
        }
        return false;
    }

    private static void makeMoves(Point from, Queue<Point> visiting, final int FAVORITE_NUMBER, int[][] map) {
        Point to = new Point(from.x - 1, from.y);
        if (move(from, to, FAVORITE_NUMBER, map))
            visiting.add(to);
        to = new Point(from.x + 1, from.y);
        if (move(from, to, FAVORITE_NUMBER, map))
            visiting.add(to);
        to = new Point(from.x, from.y - 1);
        if (move(from, to, FAVORITE_NUMBER, map))
            visiting.add(to);
        to = new Point(from.x, from.y + 1);
        if (move(from, to, FAVORITE_NUMBER, map))
            visiting.add(to);
    }

    static int solveFirst(String input, final int TARGET_X, final int TARGET_Y) {
        final int FAVORITE_NUMBER = Integer.parseInt(input);
        int[][] map = new int[MAP_SIZE][];
        for (int i = 0; i < MAP_SIZE; ++i) {
            map[i] = new int[MAP_SIZE];
            Arrays.fill(map[i], NOT_CALCULATED);
        }

        map[1][1] = 0;
        Queue<Point> visiting = new ArrayDeque<>();
        visiting.add(new Point(1, 1));

        while (map[TARGET_X][TARGET_Y] <= 0) {
            Point currentPoint = visiting.remove();
            makeMoves(currentPoint, visiting, FAVORITE_NUMBER, map);
        }

        return map[TARGET_X][TARGET_Y];
    }

    static int solveSecond(String input) {
        final int FAVORITE_NUMBER = Integer.parseInt(input);
        int[][] map = new int[MAP_SIZE][];
        for (int i = 0; i < MAP_SIZE; ++i) {
            map[i] = new int[MAP_SIZE];
            Arrays.fill(map[i], NOT_CALCULATED);
        }

        map[1][1] = 0;
        Queue<Point> visiting = new ArrayDeque<>();
        visiting.add(new Point(1, 1));

        int visitedLocations = 0;

        while (true) {
            Point currentPoint = visiting.remove();
            if (map[currentPoint.x][currentPoint.y] > 50)
                break;

            ++visitedLocations;
            makeMoves(currentPoint, visiting, FAVORITE_NUMBER, map);
        }

        return visitedLocations;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day13.class), 31, 39));
        System.out.println(solveSecond(Input.get(Day13.class)));
    }
}
