package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day10Test {
    @Test
    public void solveFirst() throws Exception {
        String instructions = "value 5 goes to bot 2\n" +
                "bot 2 gives low to bot 1 and high to bot 0\n" +
                "value 3 goes to bot 1\n" +
                "bot 1 gives low to output 1 and high to bot 0\n" +
                "bot 0 gives low to output 2 and high to output 0\n" +
                "value 2 goes to bot 2";
        assertEquals("WA", 2, Day10.solveFirst(instructions, new Day10.Pair(5, 2)));
    }

    @Test
    public void correctAnswer() throws Exception {
        assertEquals("WA", 93, Day10.solveFirst(Input.get(Day10.class), new Day10.Pair(61, 17)));
    }
}
