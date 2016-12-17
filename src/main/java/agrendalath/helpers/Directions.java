package agrendalath.helpers;

import java.awt.*;

public enum Directions {
    NORTH(new Point(0, 1), "U"),
    SOUTH(new Point(0, -1), "D"),
    WEST(new Point(-1, 0), "L"),
    EAST(new Point(1, 0), "R");

    private final Point movement;
    private final String shortName;
    private Neighbours neighbours;

    static {
        NORTH.neighbours = new Neighbours(WEST, EAST);
        SOUTH.neighbours = new Neighbours(EAST, WEST);
        WEST.neighbours = new Neighbours(SOUTH, NORTH);
        EAST.neighbours = new Neighbours(NORTH, SOUTH);
    }

    Directions(Point movement, String shortName) {
        this.movement = movement;
        this.shortName = shortName;
    }

    public void move(Point from) {
        from.setLocation(from.x + movement.x, from.y + movement.y);
    }

    public void move(Point from, int steps) {
        from.setLocation(from.x + movement.x * steps, from.y + movement.y * steps);
    }

    public Directions turn(char direction) {
        if (direction == 'L')
            return neighbours.left;
        return neighbours.right;
    }

    public String getShortName() {
        return shortName;
    }

    private static class Neighbours {
        private final Directions left;
        private final Directions right;

        private Neighbours(Directions left, Directions right) {
            this.left = left;
            this.right = right;
        }
    }
}
