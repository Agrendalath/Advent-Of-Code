package agrendalath.AOC2016;

import agrendalath.helpers.Input;

public class Day9 {
    static int solveFirst(String input) {
        int result = 0;
        for (int i = 0; i < input.length(); ++i) {
            if (input.charAt(i) == '(') {
                ++i;
                StringBuilder letters = new StringBuilder();
                for (; i < input.length() && input.charAt(i) != 'x'; ++i)
                    letters.append(input.charAt(i));
                ++i;
                StringBuilder repetitions = new StringBuilder();
                for (; i < input.length() && input.charAt(i) != ')'; ++i)
                    repetitions.append(input.charAt(i));
                i += Integer.parseInt(letters.toString());
                result += Integer.parseInt(letters.toString()) * Integer.parseInt(repetitions.toString());
            } else
                ++result;
        }
        return result;
    }

    private static long solveSecondHelper(String input, int lowerBound, int upperBound) {
        long result = 0;
        for (int i = lowerBound; i < upperBound; ++i) {
            if (input.charAt(i) == '(') {
                ++i;
                StringBuilder letters = new StringBuilder();
                for (; i < input.length() && input.charAt(i) != 'x'; ++i)
                    letters.append(input.charAt(i));
                ++i;
                StringBuilder repetitions = new StringBuilder();
                for (; i < input.length() && input.charAt(i) != ')'; ++i)
                    repetitions.append(input.charAt(i));
                result += Integer.parseInt(repetitions.toString()) * solveSecondHelper(
                        input,
                        i + 1,
                        i + Integer.parseInt(letters.toString()) + 1
                );
                i += Integer.parseInt(letters.toString());
            } else
                ++result;
        }
        return result;
    }

    static long solveSecond(String input) {
        return solveSecondHelper(input, 0, input.length());
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day9.class)));
        System.out.println(solveSecond(Input.get(Day9.class)));
    }
}
