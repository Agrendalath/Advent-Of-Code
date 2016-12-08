package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {
    static int lightRect(boolean[][] display, int x, int y) {
        int light = 0;

        for (int i = 0; i < x && i < display[0].length; ++i)
            for (int j = 0; j < y && j < display.length; ++j)
                if (!display[j][i]) {
                    display[j][i] = true;
                    ++light;
                }

        return light;
    }

    static void rotateRow(boolean[][] display, int row, int rotation) {
        rotation %= display[row].length;
        boolean[][] tmp = new boolean[display.length][display[0].length];
        for (int i = 0; i < tmp.length; ++i)
            System.arraycopy(display[i], 0, tmp[i], 0, display[i].length);

        for (int i = 0; i < display[row].length; ++i) {
            int current = i - rotation;
            if (current < 0)
                current += display[row].length;
            display[row][i] = tmp[row][current];
        }
    }

    static void rotateColumn(boolean[][] display, int column, int rotation) {
        rotation %= display.length;
        boolean[][] tmp = new boolean[display.length][display[0].length];
        for (int i = 0; i < tmp.length; ++i)
            System.arraycopy(display[i], 0, tmp[i], 0, display[i].length);

        for (int i = 0; i < display.length; ++i) {
            int current = i - rotation;
            if (current < 0)
                current += display.length;
            display[i][column] = tmp[current][column];
        }
    }

    static int solveFirst(String input, final int Y_SIZE, final int X_SIZE) {
        int result = 0;
        Pattern pattern = Pattern.compile("\\d+");
        Pattern column = Pattern.compile("x=\\d+");
        boolean[][] display = new boolean[Y_SIZE][X_SIZE];

        for (String line : input.split("\n")) {
            Matcher matcher = pattern.matcher(line);
            //noinspection ResultOfMethodCallIgnored
            matcher.find();
            int x = Integer.parseInt(matcher.group());
            //noinspection ResultOfMethodCallIgnored
            matcher.find();
            int y = Integer.parseInt(matcher.group());

            if (line.startsWith("rect"))
                result += lightRect(display, x, y);
            else if (column.matcher(line).find())
                rotateColumn(display, x, y);
            else
                rotateRow(display, x, y);

        }
        printIt(display);
        return result;
    }

    private static void printIt(boolean[][] display) {
        for (boolean[] aDisplay : display) {
            for (int j = 0; j < display[0].length; ++j) {
                System.out.print(aDisplay[j] ? "#" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day8.class), 6, 50));
    }
}
