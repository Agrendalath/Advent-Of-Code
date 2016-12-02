package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.awt.*;

public class Day2 {
    static String solveFirst(String input) {
        int position = 5;
        StringBuilder code = new StringBuilder();

        for (String line : input.split("\n")) {
            for (char instruction : line.toCharArray()) {
                switch (instruction) {
                    case 'U':
                        if (position > 3)
                            position -= 3;
                        break;
                    case 'D':
                        if (position < 7)
                            position += 3;
                        break;
                    case 'L':
                        if ((position - 1) % 3 != 0)
                            position -= 1;
                        break;
                    case 'R':
                        if (position % 3 != 0)
                            position += 1;
                        break;
                }
            }
            code.append(Integer.toString(position));
        }
        return code.toString();
    }

    static String solveSecond(String input) {
        int[][] arr = {{0, 0, 1, 0, 0}, {0, 2, 3, 4, 0}, {5, 6, 7, 8, 9}, {0, 10, 11, 12, 0}, {0, 0, 13, 0, 0}};
        Point position = new Point(0, 2);
        StringBuilder code = new StringBuilder();

        for (String line : input.split("\n")) {
            for (char instruction : line.toCharArray()) {
                switch (instruction) {
                    case 'U':
                        if (position.y != 0 && arr[position.y-1][position.x] != 0)
                            position.y -= 1;
                        break;
                    case 'D':
                        if (position.y != arr.length-1 && arr[position.y+1][position.x] != 0)
                            position.y += 1;
                        break;
                    case 'L':
                        if (position.x != 0 && arr[position.y][position.x-1] != 0)
                            position.x -=1;
                        break;
                    case 'R':
                        if (position.x != arr.length-1 && arr[position.y][position.x+1] != 0)
                            position.x += 1;
                        break;
                }

            }
            code.append(Integer.toHexString(arr[position.y][position.x]).toUpperCase());
        }
        return code.toString();
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day2.class)));
        System.out.println(solveSecond(Input.get(Day2.class)));
    }
}
