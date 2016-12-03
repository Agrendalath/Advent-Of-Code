package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.StringTokenizer;

public class Day3 {
    private static int solveFirst(String input) {
        int counter = 0;
        for (String line : input.split("\n")) {
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            int a = Integer.parseInt(stringTokenizer.nextToken());
            int b = Integer.parseInt(stringTokenizer.nextToken());
            int c = Integer.parseInt(stringTokenizer.nextToken());
            if (a + b > c && a + c > b && b + c > a)
                ++counter;
        }
        return counter;
    }

    private static int solveSecond(String input) {
        int counter = 0;
        String[] splitInput = input.split("\n");
        for (int i=2; i<splitInput.length; i+=3) {
            StringTokenizer first = new StringTokenizer(splitInput[i-2]);
            StringTokenizer second = new StringTokenizer(splitInput[i-1]);
            StringTokenizer third = new StringTokenizer(splitInput[i]);
            for (int j=0; j<3; ++j) {
                int a = Integer.parseInt(first.nextToken());
                int b = Integer.parseInt(second.nextToken());
                int c = Integer.parseInt(third.nextToken());
                if (a + b > c && a + c > b && b + c > a)
                    ++counter;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day3.class)));
        System.out.println(solveSecond(Input.get(Day3.class)));
    }
}
