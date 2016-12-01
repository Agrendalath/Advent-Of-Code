package agrendalath.helpers;

import java.awt.*;

public enum Directions {
    NORTH(new Point(0, 1)),
    SOUTH(new Point(0, -1)),
    WEST(new Point(-1, 0)),
    EAST(new Point(1, 0));

    private final Point movement;
    private Neighbours neighbours;

    static {
        NORTH.neighbours = new Neighbours(WEST, EAST);
        SOUTH.neighbours = new Neighbours(EAST, WEST);
        WEST.neighbours = new Neighbours(SOUTH, NORTH);
        EAST.neighbours = new Neighbours(NORTH, SOUTH);
    }

    Directions(Point movement) {
        this.movement = movement;
    }

    public void move(Point from, int steps) {
        from.setLocation(from.x + movement.x * steps, from.y + movement.y * steps);
    }

    public Directions turn(char direction) {
        if (direction == 'L')
            return neighbours.left;
        return neighbours.right;
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
