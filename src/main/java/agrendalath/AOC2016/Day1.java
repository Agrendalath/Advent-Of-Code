package agrendalath.AOC2016;

import agrendalath.helpers.Directions;
import agrendalath.helpers.Input;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    static int solveFirst(String input) {
        Point position = new Point(0, 0);
        Directions direction = Directions.NORTH;

        for (String movement : input.replaceAll(" ", "").split(",")) {
            direction = direction.turn(movement.charAt(0));
            direction.move(position, Integer.parseInt(movement.substring(1)));
        }

        return Math.abs(position.x) + Math.abs(position.y);
    }

    static int solveSecond(String input) {
        List<Line2D> paths = new ArrayList<>();
        Point position = new Point(0, 0);
        Point previousPosition;
        Directions direction = Directions.NORTH;
        boolean vertical = true;

        for (String movement : input.replaceAll(" ", "").split(",")) {
            vertical = !vertical;
            previousPosition = new Point(position);

            direction = direction.turn(movement.charAt(0));
            direction.move(position, Integer.parseInt(movement.substring(1)));

            Line2D.Float path = new Line2D.Float(previousPosition, position);

            Point closestPoint = null; // in case of the current line intersecting more than one line
            int closestPointDistance = Integer.MAX_VALUE;


            for (int i = vertical ? 0 : 1; i < paths.size(); i += 2) {
                Line2D checkedPath = paths.get(i);

                if (!checkedPath.getP2().equals(path.getP1()) && checkedPath.intersectsLine(path)) {
                    Point currentPoint;
                    if (vertical)
                        currentPoint = new Point((int) path.getX1(), (int) checkedPath.getY1());

                    else
                        currentPoint = new Point((int) checkedPath.getX1(), (int) path.getY1());

                    if (path.getP1().distance(currentPoint) < closestPointDistance) {
                        closestPointDistance = (int) path.getP1().distance(currentPoint);
                        closestPoint = currentPoint;
                    }
                }

                if (i + 2 >= paths.size() && closestPoint != null)
                    return Math.abs(closestPoint.x) + Math.abs(closestPoint.y);
            }

            paths.add(path);
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println("First: " + solveFirst(Input.get(Day1.class)));
        System.out.println("Second: " + solveSecond(Input.get(Day1.class)));
    }
}
