package agrendalath.AOC2016;

import agrendalath.helpers.Directions;
import agrendalath.helpers.Input;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;
import java.util.EnumSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Day17 {
    private static final Set<Character> OPEN_DOORS = Sets.newHashSet('b', 'c', 'd', 'e', 'f');

    private static EnumSet<Directions> getPossibleMovements(String input) {
        EnumSet<Directions> directions = EnumSet.noneOf(Directions.class);
        String md5 = DigestUtils.md5Hex(input);
        if (OPEN_DOORS.contains(md5.charAt(0)))
            directions.add(Directions.NORTH);
        if (OPEN_DOORS.contains(md5.charAt(1)))
            directions.add(Directions.SOUTH);
        if (OPEN_DOORS.contains(md5.charAt(2)))
            directions.add(Directions.WEST);
        if (OPEN_DOORS.contains(md5.charAt(3)))
            directions.add(Directions.EAST);

        return directions;
    }

    static String solveFirst(String input) {
        Point winningPosition = new Point(3, 0);
        PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.add(new Position(new Point(0, 3), ""));
        while (!queue.isEmpty()) {
            Position currentPosition = queue.remove();
            if (currentPosition.getPosition().equals(winningPosition))
                return currentPosition.getPath();

            EnumSet<Directions> directions = getPossibleMovements(input + currentPosition.getPath());
            for (Directions direction : directions) {
                Point nextPosition = new Point(currentPosition.getPosition());
                direction.move(nextPosition);
                if (nextPosition.x >= 0 && nextPosition.y >= 0 && nextPosition.x < 4 && nextPosition.y < 4)
                    queue.add(new Position(nextPosition, currentPosition.getPath() + direction.getShortName()));
            }
        }
        return "NO WAY";
    }

    public static int solveSecond(String input) {
        Point winningPosition = new Point(3, 0);
        PriorityQueue<Position> queue = new PriorityQueue<>();
        queue.add(new Position(new Point(0, 3), ""));

        int maxPath = -1;
        while (!queue.isEmpty()) {
            Position currentPosition = queue.remove();
            EnumSet<Directions> directions = getPossibleMovements(input + currentPosition.getPath());
            for (Directions direction : directions) {
                Point nextPosition = new Point(currentPosition.getPosition());
                direction.move(nextPosition);
                if (nextPosition.equals(winningPosition))
                    maxPath = currentPosition.getPath().length() + 1;
                else if (nextPosition.x >= 0 && nextPosition.y >= 0 && nextPosition.x < 4 && nextPosition.y < 4)
                    queue.add(new Position(nextPosition, currentPosition.getPath() + direction.getShortName()));
            }
        }
        return maxPath;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day17.class)));
        System.out.println(solveSecond(Input.get(Day17.class)));
    }

    private static class Position implements Comparable<Position> {
        private final Point position;
        private final String path;

        private Position(Point position, String path) {
            this.position = position;
            this.path = path;
        }

        Point getPosition() {
            return position;
        }

        String getPath() {
            return path;
        }

        @Override
        public int compareTo(Position position) {
            if (path.length() != position.path.length())
                return Integer.compare(path.length(), position.path.length());

            return Integer.compare(position.position.x + position.position.y, this.position.x + this.position.y);
        }
    }
}
