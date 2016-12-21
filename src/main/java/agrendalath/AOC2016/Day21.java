package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 {
    private static final Pattern POSITION_PATTERN = Pattern.compile("position (\\d+)");
    private static final Pattern LETTER_PATTERN = Pattern.compile("letter (\\w)");

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void swap(String instruction, StringBuilder result) {
        int position1;
        int position2;
        char letter1;
        char letter2;

        if (instruction.contains("position")) {
            Matcher matcher = POSITION_PATTERN.matcher(instruction);
            matcher.find();
            position1 = Integer.parseInt(matcher.group(1));
            matcher.find();
            position2 = Integer.parseInt(matcher.group(1));

            letter1 = result.charAt(position1);
            letter2 = result.charAt(position2);
        } else {
            Matcher matcher = LETTER_PATTERN.matcher(instruction);
            matcher.find();
            letter1 = matcher.group(1).charAt(0);
            matcher.find();
            letter2 = matcher.group(1).charAt(0);

            position1 = result.indexOf(Character.toString(letter1));
            position2 = result.indexOf(Character.toString(letter2));
        }

        result.setCharAt(position2, letter1);
        result.setCharAt(position1, letter2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void rotate(String instruction, StringBuilder result) {
        Pattern number = Pattern.compile("\\d+");
        if (instruction.contains("step")) {
            Matcher matcher = number.matcher(instruction);
            matcher.find();
            int steps = Integer.parseInt(matcher.group()) % result.length();
            if (instruction.contains("left"))
                result.append(result.subSequence(0, steps)).delete(0, steps);
            else {
                int previousLength = result.length();
                result.append(result.subSequence(0, previousLength - steps)).delete(0, previousLength - steps);
            }
        } else {
            Matcher matcher = LETTER_PATTERN.matcher(instruction);
            matcher.find();
            char letter = matcher.group(1).charAt(0);
            int steps = result.indexOf(Character.toString(letter));
            if (steps++ >= 4)
                ++steps;
            steps %= result.length();
            int previousLength = result.length();
            result.append(result.subSequence(0, previousLength - steps)).delete(0, previousLength - steps);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void reverse(String instruction, StringBuilder result) {
        Pattern number = Pattern.compile("\\d+");
        Matcher matcher = number.matcher(instruction);
        matcher.find();
        int position1 = Integer.parseInt(matcher.group());
        matcher.find();
        int position2 = Integer.parseInt(matcher.group());

        result.replace(position1, position2 + 1,
                new StringBuilder(result.substring(position1, position2 + 1)).reverse().toString());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void move(String instruction, StringBuilder result) {
        Matcher matcher = POSITION_PATTERN.matcher(instruction);
        matcher.find();
        int position1 = Integer.parseInt(matcher.group(1));
        matcher.find();
        int position2 = Integer.parseInt(matcher.group(1));

        char letter = result.charAt(position1);
        result.deleteCharAt(position1);
        result.insert(position2, letter);
    }

    static String solveFirst(String input, String initial) {
        StringBuilder result = new StringBuilder(initial);
        for (String line : input.split("\n")) {
            String[] instruction = line.split(" ");
            switch (instruction[0]) {
                case "swap":
                    swap(line, result);
                    break;
                case "rotate":
                    rotate(line, result);
                    break;
                case "reverse":
                    reverse(line, result);
                    break;
                case "move":
                    move(line, result);
                    break;
                default:
                    throw new NotImplementedException();
            }
        }
        return result.toString();
    }

    static int[] generateRotations(int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; ++i) {
            int nextIndex = i + i;
            if (i >= 4)
                ++nextIndex;
            ++nextIndex;
            result[nextIndex % size] = i;
        }
        return result;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void rotateBack(String instruction, StringBuilder result, int[] rotations) {
        Matcher matcher = LETTER_PATTERN.matcher(instruction);
        matcher.find();
        char letter = matcher.group(1).charAt(0);
        int currentPosition = result.indexOf(Character.toString(letter));
        int initialPosition = rotations[currentPosition];
        StringBuilder nextInstruction = new StringBuilder("rotate ");
        if (currentPosition == initialPosition)
            return;

        if (currentPosition > initialPosition)
            nextInstruction.append("left ");
        else
            nextInstruction.append("right ");

        nextInstruction.append(Integer.toString(Math.abs(currentPosition - initialPosition))).append(" steps");
        rotate(nextInstruction.toString(), result);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void moveBack(String instruction, StringBuilder result) {
        Matcher matcher = POSITION_PATTERN.matcher(instruction);
        matcher.find();
        String position1 = matcher.group(1);
        matcher.find();
        String position2 = matcher.group(1);

        move(instruction.replaceAll(position2, "#").replaceAll(position1, position2).replaceAll("#", position1),
                result);
    }

    static String solveSecond(String input, String initial) {
        int[] rotations = generateRotations(initial.length());
        StringBuilder result = new StringBuilder(initial);
        String[] instructions = input.split("\n");
        for (int i = instructions.length - 1; i >= 0; --i) {
            String[] instruction = instructions[i].split(" ");
            switch (instruction[0]) {
                case "swap":
                    swap(instructions[i], result);
                    break;
                case "rotate":
                    if (instructions[i].contains("left"))
                        rotate(instructions[i].replaceAll("left", "right"), result);
                    else if (instructions[i].contains("right"))
                        rotate(instructions[i].replaceAll("right", "left"), result);
                    else
                        rotateBack(instructions[i], result, rotations);
                    break;
                case "reverse":
                    reverse(instructions[i], result);
                    break;
                case "move": {
                    moveBack(instructions[i], result);
                    break;
                }
                default:
                    throw new NotImplementedException();
            }
        }
        return result.toString();
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day21.class), "abcdefgh"));
        System.out.println(solveSecond(Input.get(Day21.class), "fbgdceah"));
    }
}
