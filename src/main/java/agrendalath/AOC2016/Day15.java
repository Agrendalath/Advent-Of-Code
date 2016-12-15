package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    static int solveFirst(String input) {
        String[] parsedInput = input.split("\n");
        Disc[] discs = new Disc[parsedInput.length];

        Pattern pattern = Pattern.compile("\\d+");
        int index = 0;
        for (String line : parsedInput) {
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            matcher.find();
            int positions = Integer.parseInt(matcher.group());
            matcher.find();
            matcher.find();
            int currentPosition = Integer.parseInt(matcher.group());
            discs[index++] = new Disc(positions, currentPosition + index);
        }

        int time = 0;
        boolean passing = false;
        while (!passing) {
            passing = true;
            int timeShift = 0;

            for (int i = 0; i < discs.length && passing; ++i) {
                if (discs[i].getCurrentPosition() != 0) {
                    passing = false;
                    timeShift = discs[i].getPositions() - discs[i].getCurrentPosition();
                }
            }

            if (!passing)
                for (Disc disc : discs)
                    disc.toTheFuture(timeShift);

            time += timeShift;
        }

        return time;
    }

    static int solveSecond(String input) {
        return solveFirst(input + "\nDisc #7 has 11 positions; at time=0, it is at position 0.");
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day15.class)));
        System.out.println(solveSecond(Input.get(Day15.class)));
    }

    private static class Disc {
        private final int positions;
        private int currentPosition;


        private Disc(int positions, int currentPosition) {
            this.positions = positions;
            this.currentPosition = currentPosition % positions;
        }

        int getPositions() {
            return positions;
        }

        int getCurrentPosition() {
            return currentPosition;
        }

        void toTheFuture(int time) {
            currentPosition = (currentPosition + time) % positions;
        }
    }
}
