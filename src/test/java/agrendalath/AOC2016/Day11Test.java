package agrendalath.AOC2016;

import agrendalath.helpers.Input;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Day11Test {
    @Test
    public void solveFirst() throws Exception {
        String instructions = "The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.\n" +
                "The second floor contains a hydrogen generator.\n" +
                "The third floor contains a lithium generator.\n" +
                "The fourth floor contains nothing relevant.\n";

        assertEquals("WA", 11, Day11.solveFirst(instructions));
    }

    @Test
    public void solveActualInput() throws Exception {
        int answer = Day11.solveFirst(Input.get(Day11.class));
        assertTrue("Too high, " + answer, answer < 37);
        assertTrue("Too low:, " + answer, 11 < answer);
        assertEquals("WA", 33, answer);
    }
}
