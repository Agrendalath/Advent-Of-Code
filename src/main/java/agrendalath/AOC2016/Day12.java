package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.HashMap;
import java.util.Map;

public class Day12 {
    static int solveFirst(String input) {
        Map<Character, Integer> registers = new HashMap<>();
        for (char c = 'a'; c <= 'd'; ++c)
            registers.put(c, 0);

        registers.put('c', 1);

        String[] instructions = input.split("\n");

        for (int i = 0; i < instructions.length; ++i) {
            String[] instruction = instructions[i].split(" ");
            switch (instruction[0]) {
                case "cpy":
                    if (Character.isLowerCase(instruction[1].charAt(0)))
                        registers.put(instruction[2].charAt(0), registers.get(instruction[1].charAt(0)));
                    else
                        registers.put(instruction[2].charAt(0), Integer.parseInt(instruction[1]));
                    break;
                case "inc":
                    registers.put(instruction[1].charAt(0), registers.get(instruction[1].charAt(0)) + 1);
                    break;
                case "dec":
                    registers.put(instruction[1].charAt(0), registers.get(instruction[1].charAt(0)) - 1);
                    break;
                case "jnz":
                    if ((Character.isLowerCase(instruction[1].charAt(0)) &&
                            registers.get(instruction[1].charAt(0)) != 0) ||
                            (!Character.isLowerCase(instruction[1].charAt(0)) &&
                            Integer.parseInt(instruction[1]) != 0)) {
                        int jump = Integer.parseInt(instruction[2]);
                        i += jump - (jump != 0 ? 1 : 0);
                    }
                    break;
            }
        }
        return registers.get('a');
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day12.class)));
    }
}
