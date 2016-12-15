package agrendalath.AOC2016;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day15Test {
    @Test
    public void solveFirst() throws Exception {
        String input = "Disc #1 has 5 positions; at time=0, it is at position 4.\n" +
                "Disc #2 has 2 positions; at time=0, it is at position 1.";
        assertEquals(5, Day15.solveFirst(input));
    }

}
