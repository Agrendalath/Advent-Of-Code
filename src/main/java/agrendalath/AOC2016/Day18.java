package agrendalath.AOC2016;

import agrendalath.helpers.Input;

public class Day18 {
    private static boolean isTrap(boolean left, boolean right) {
        return left ^ right;
    }

    static int solveFirst(String input, int rows) {
        int safeTiles = 0;
        final int width = input.length();
        StringBuilder output = new StringBuilder(input);
        for (int i = 0; i < output.length(); ++i)
            if (output.charAt(i) == '.')
                ++safeTiles;

        for (int i = width; i < rows * width; ++i) {
            boolean left = false;
            boolean right = false;

            if (i % width != 0)
                left = output.charAt(i - width - 1) == '^';
            if ((i + 1) % width != 0)
                right = output.charAt(i - width + 1) == '^';

            boolean trap = isTrap(left, right);
            output.append(trap ? '^' : '.');
            if (!trap)
                ++safeTiles;
        }

        return safeTiles;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day18.class), 40));
        System.out.println(solveFirst(Input.get(Day18.class), 400000));
    }
}
